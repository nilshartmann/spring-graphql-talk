export interface NewRating {
  comment: string;
  stars: string;
}

export interface AuthInfoState {
  auth: {
    userId: string;
    username: string;
  };
}

export interface AuthErrorState {
  error: string;
}

type Present<T> = T extends undefined ? never : T extends null ? never : T;
export function assertValidData<T>(data: T): asserts data is Present<T> {
  if (!data) {
    throw new Error("Data is not defined?!");
  }
}

export function isValidAuth(auth: AuthInfoState | AuthErrorState | null | undefined): auth is AuthInfoState {
  return !!auth && "auth" in auth;
}

export function assertValidAuth(auth: AuthInfoState | AuthErrorState | null | undefined): asserts auth is AuthInfoState {
  if (!auth || "error" in auth) {
    throw new Error("Not authenticated");
  }
}
