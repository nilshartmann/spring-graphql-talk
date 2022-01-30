import BeerPage from "BeerPage";
import { OverviewPage } from "OverviewPage";
import * as React from "react";
import { BrowserRouter as Router, Route } from "react-router-dom";
import ShopPage from "ShopPage";
import styles from "./BeerRatingApp.module.css";
import Footer from "./Footer";
import Header from "./Header";

class BeerRatingApp extends React.Component {
  render() {
    return (
      <Router>
        <div className={styles.BeerRatingApp}>
          <Header />
          <div className={styles.Main}>
            <Route exact path="/" component={OverviewPage} />
            <Route path="/beer/:beerId" component={BeerPage} />
            <Route path="/shop/:shopId" component={ShopPage} />
          </div>
          <Footer />
        </div>
      </Router>
    );
  }
}

export default BeerRatingApp;
