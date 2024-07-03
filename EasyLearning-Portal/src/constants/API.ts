interface APIRequest {
  method: string;
  url: string;
  requestBody: any;
  token: string | null;
}

export const API: APIRequest[] = [];
