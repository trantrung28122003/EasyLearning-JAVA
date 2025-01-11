class User {
  final String id;
  final String userName;
  final String email;
  final String fullName;
  final String dayOfBirth;
  final String? imageUrl;
  final List<Role> roles;
  final String dateCreate;
  final String dateChange;
  final String changedBy;
  final bool deleted;
  User({
    required this.id,
    required this.userName,
    required this.email,
    required this.fullName,
    required this.dayOfBirth,
    this.imageUrl,
    required this.roles,
    required this.dateCreate,
    required this.dateChange,
    required this.changedBy,
    required this.deleted,
  });

  factory User.fromJson(Map<String, dynamic> json) {
    var roleList = json['roles'] as List;
    List<Role> rolesList = roleList.map((i) => Role.fromJson(i)).toList();

    return User(
      id: json['id'],
      userName: json['userName'],
      email: json['email'],
      fullName: json['fullName'],
      dayOfBirth: json['dayOfBirth'],
      imageUrl: json['imageUrl'],
      roles: rolesList,
      dateCreate: json['dateCreate'],
      dateChange: json['dateChange'],
      changedBy: json['changedBy'],
      deleted: json['deleted'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'userName': userName,
      'email': email,
      'fullName': fullName,
      'dayOfBirth': dayOfBirth,
      'imageUrl': imageUrl,
      'roles': roles.map((role) => role.toJson()).toList(),
      'dateCreate': dateCreate,
      'dateChange': dateChange,
      'changedBy': changedBy,
      'deleted': deleted,
    };
  }
}

class Role {
  final String id;
  final String name;

  Role({required this.id, required this.name});

  factory Role.fromJson(Map<String, dynamic> json) {
    return Role(
      id: json['id'],
      name: json['name'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'name': name,
    };
  }
}
