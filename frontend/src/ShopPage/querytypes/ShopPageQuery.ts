/* tslint:disable */
/* eslint-disable */
// @generated
// This file was automatically generated and should not be edited.

// ====================================================
// GraphQL query operation: ShopPageQuery
// ====================================================

export interface ShopPageQuery_shop_address {
  __typename: "Address";
  /**
   *  The street
   */
  street: string;
  /**
   *  Postal code
   */
  postalCode: string;
  /**
   *  Name of the city
   */
  city: string;
  /**
   *  Country of this Address
   */
  country: string;
}

export interface ShopPageQuery_shop_beers {
  __typename: "Beer";
  /**
   *  Unique, immutable Id, that identifies this Beer
   */
  id: string;
  /**
   *  The name of the beer
   */
  name: string;
}

export interface ShopPageQuery_shop {
  __typename: "Shop";
  /**
   *  Unique ID of this shop
   */
  id: string;
  /**
   *  The name of the shop
   */
  name: string;
  openingHours: string;
  /**
   *  Address of the shop
   */
  address: ShopPageQuery_shop_address;
  /**
   *  All Beers this shop sells
   */
  beers: ShopPageQuery_shop_beers[];
}

export interface ShopPageQuery {
  shop: ShopPageQuery_shop | null;
}

export interface ShopPageQueryVariables {
  shopId: string;
}
