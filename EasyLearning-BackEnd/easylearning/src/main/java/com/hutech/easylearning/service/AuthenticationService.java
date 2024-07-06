package com.hutech.easylearning.service;

import com.hutech.easylearning.dto.reponse.AuthenticationResponse;
import com.hutech.easylearning.dto.reponse.IntrospectResponse;
import com.hutech.easylearning.dto.request.AuthenticationRequest;
import com.hutech.easylearning.dto.request.IntrospectRequest;
import com.hutech.easylearning.dto.request.LogoutRequest;
import com.hutech.easylearning.dto.request.RefreshRequest;
import com.hutech.easylearning.entity.InvalidatedToken;
import com.hutech.easylearning.entity.User;
import com.hutech.easylearning.exception.AppException;
import com.hutech.easylearning.exception.ErrorCode;
import com.hutech.easylearning.repository.InvalidatedTokenRepository;
import com.hutech.easylearning.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimNames;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository ;
    @Autowired
    private InvalidatedTokenRepository invalidatedTokenRepository;

    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @Value("${jwt.valid-duration}")
    protected Long VALID_DURATION;

    @Value("${jwt.refreshable-duration}")
    protected Long REFRESHABLE_DURATION;

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        boolean isValid = true;
        try {
            verifyToken(token, false);
        } catch (AppException e) {
            isValid = false;
        }


        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request)
    {
        var user = userRepository.findByUserName(request.getUserName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if(!authenticated)
        {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

        var token = generateToken(user);
        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }

    public void logout(LogoutRequest request) throws ParseException, JOSEException {
       try{
           var signToken = verifyToken(request.getToken(), true);
           String jit = signToken.getJWTClaimsSet().getJWTID();
           Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();
           InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                   .id(jit)
                   .expiryTime(expiryTime)
                   .build();
           invalidatedTokenRepository.save(invalidatedToken);
       }catch (AppException exception){
           log.info("Token already expired");

       }


    }

    public AuthenticationResponse refreshToken (RefreshRequest  request) throws ParseException, JOSEException {
        var signedJWT = verifyToken(request.getToken(), true);
        String jit = signedJWT.getJWTClaimsSet().getJWTID();
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();
        invalidatedTokenRepository.save(invalidatedToken);

        var userName = signedJWT.getJWTClaimsSet().getSubject();
        var user = userRepository.findByUserName(userName).orElseThrow(
                () -> new AppException((ErrorCode.UNAUTHENTICATED)));

        var token = generateToken(user);
        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException
    {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (!isRefresh) ? signedJWT.getJWTClaimsSet().getExpirationTime()
                : new Date(signedJWT.getJWTClaimsSet()
                .getIssueTime().toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                .toEpochMilli());

        var verified = signedJWT.verify(verifier);
        if (!(verified && expiryTime.after(new Date())))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        if(invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }
    private String generateToken(User user)
    {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUserName())
                .issuer("devteria.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        }
        catch (JOSEException e)
        {
            //log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }
    private String buildScope(User user)
    {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getRoles()))
        {
           user.getRoles().forEach(role -> {
               stringJoiner.add(role.getName());

           });
        }
        return stringJoiner.toString();
    }
}
