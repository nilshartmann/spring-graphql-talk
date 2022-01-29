import * as React from "react";
import { assertValidData, AuthErrorState, AuthInfoState } from "types";
import { useLoginMutation } from "./generated/graphql";

interface IAuthContext {
  auth: AuthInfoState | AuthErrorState | null;
  login(userId: string): void;
}

const AuthContext = React.createContext<IAuthContext>({
  auth: null,
  login() {},
});

const getAuthToken = () => sessionStorage.getItem("auth-token");
const setAuthToken = (authToken: string | null) =>
  authToken ? sessionStorage.setItem("auth-token", authToken) : sessionStorage.removeItem("auth-token");

interface AuthProviderState {
  auth: AuthInfoState | AuthErrorState | null;
}

type AuthProviderProps = {
  children: React.ReactElement;
};

function AuthProvider({ children }: AuthProviderProps) {
  const [loginMutation] = useLoginMutation();
  const [authState, setAuthState] = React.useState<AuthProviderState>({
    auth: null,
  });

  const login = React.useCallback(
    async function login(username: string) {
      setAuthToken(null);

      const loginResult = await loginMutation({ variables: { username } });
      if (!loginResult.data) {
        setAuthState({
          auth: {
            error: `Could not authenticate (${loginResult.errors})`,
          },
        });
        return;
      }

      const { error, authentication } = loginResult.data.login;

      if (error) {
        setAuthState({
          auth: {
            error: `Could not authenticate (${error})`,
          },
        });
        return;
      }

      assertValidData(authentication);

      setAuthToken(authentication.authToken);

      setAuthState({
        auth: {
          auth: {
            userId: authentication.userId,
            username: authentication.username,
          },
        },
      });
    },
    [loginMutation]
  );

  return (
    <AuthContext.Provider
      value={{
        auth: authState.auth,
        login: login,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}

function useAuthContext() {
  const authContext = React.useContext(AuthContext);
  return authContext;
}

export { AuthProvider, useAuthContext, setAuthToken, getAuthToken };
