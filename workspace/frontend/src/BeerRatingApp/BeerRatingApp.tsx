import BeerPage from "BeerPage";
import { OverviewPage } from "OverviewPage";
import * as React from "react";
import { BrowserRouter as Router, Route } from "react-router-dom";
import ShopPage from "ShopPage";
import styles from "./BeerRatingApp.module.css";
import Footer from "./Footer";
import Header from "./Header";
import ShopListPage from "../ShopDemo/ShopListPage";
import SingeShopPage from "../ShopDemo/SingeShopPage";

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

            {/* Live Coding Demo only: */}
            <Route exact path="/shops" component={ShopListPage} />
            <Route exact path="/shops/:shopId" component={SingeShopPage} />
          </div>
          <Footer />
        </div>
      </Router>
    );
  }
}

export default BeerRatingApp;
