import * as React from "react";
import styles from "./Form.module.css";

interface LoginFormProps {
  error: string | null;
  login: (userId: string, password: string) => void;
}

interface LoginFormState {
  username: string;
  password: string;
}

export default function LF({ error, login }: LoginFormProps) {
  const [username, setUsername] = React.useState("");
  const [password, setPassword] = React.useState("");

  const onLoginClick = (e: React.SyntheticEvent<HTMLElement>) => {
    e.preventDefault();
    login(username, password);
  };

  const buttonEnabled = !!username;

  return (
    <div className={styles.Form}>
      <div className={styles.Hint}>Please login first</div>
      <form>
        <fieldset>
          <div>
            <label>Your Username:</label>{" "}
            <input
              type="text"
              value={username}
              onChange={(e) => setUsername(e.currentTarget.value)}
              onKeyPress={(e) => (e.keyCode === 13 ? onLoginClick(e) : null)}
            />
          </div>
          <div>
            <label>Password:</label>{" "}
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.currentTarget.value)}
              onKeyPress={(e) => (e.keyCode === 13 ? onLoginClick(e) : null)}
            />
          </div>
          {error && <div>Could not login: {error}</div>}
          <div>
            <button disabled={!buttonEnabled} onClick={onLoginClick}>
              Login
            </button>
          </div>
        </fieldset>
      </form>
    </div>
  );
}
//
// export default class LoginForm extends React.Component<LoginFormProps, LoginFormState> {
//   readonly state: LoginFormState = {
//     username: "",
//     password: "",
//   };
//
//   onUserIdChange = (e: React.SyntheticEvent<HTMLInputElement>) => {
//     this.setState({ username: e.currentTarget.value });
//   };
//
//   render() {
//     const { error } = this.props;
//     const { username } = this.state;
//
//     const buttonEnabled = !!username;
//
//     return (
//       <div className={styles.Form}>
//         <div className={styles.Hint}>Please login first</div>
//         <form>
//           <fieldset>
//             <div>
//               <label>Your login:</label>{" "}
//               <input
//                 type="text"
//                 value={username}
//                 onChange={this.onUserIdChange}
//                 onKeyPress={(e) => (e.keyCode === 13 ? this.onLoginClick(e) : null)}
//               />
//             </div>
//             {error && <div>Could not login: {error}</div>}
//             <div>
//               <button disabled={!buttonEnabled} onClick={this.onLoginClick}>
//                 Login
//               </button>
//             </div>
//           </fieldset>
//         </form>
//       </div>
//     );
//   }
// }
