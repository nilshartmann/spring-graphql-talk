import * as React from "react";
import { RouteComponentProps, withRouter } from "react-router";
import styles from "./Header.module.css";

interface HeaderProps extends RouteComponentProps {
  children?: React.ReactNode;
}

const Header = ({ history }: HeaderProps) => {
  return (
    <header className={styles.Header}>
      <div className={styles.MainHeader}>
        <h1 onClick={() => history.push("/")}>Beer Advisor</h1>
      </div>
    </header>
  );
};

export default withRouter(Header);
