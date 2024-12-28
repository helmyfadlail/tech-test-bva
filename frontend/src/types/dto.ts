export interface responseData<T> {
  data: T;
  errors: string;
  paging: pagingResponse;
}

export interface pagingResponse {
  currentPage: number;
  totalPage: number;
  size: number;
}

export interface memberResponse {
  id: string;
  name: string;
  superior: string;
  position: string;
  pictureUrl: string;
}

export interface pictureUrlRequest {
  file: File | null;
  name: string;
  preview: string;
}

export interface loginRequest {
  username: string;
  password: string;
}

export interface registerRequest {
  username: string;
  email: string;
  password: string;
  confirmPassword: string;
}

export interface createMemberRequest {
  name: string;
  superior: string;
  position: string;
}

export interface updateMemberRequest {
  id: string;
  name: string;
  superior: string;
  position: string;
}
