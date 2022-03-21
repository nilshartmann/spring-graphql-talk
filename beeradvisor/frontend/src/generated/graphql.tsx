import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client';
export type Maybe<T> = T | null;
export type InputMaybe<T> = Maybe<T>;
export type Exact<T extends { [key: string]: unknown }> = { [K in keyof T]: T[K] };
export type MakeOptional<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]?: Maybe<T[SubKey]> };
export type MakeMaybe<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]: Maybe<T[SubKey]> };
const defaultOptions =  {}
/** All built-in and custom scalars, mapped to their actual values */
export type Scalars = {
  ID: string;
  String: string;
  Boolean: boolean;
  Int: number;
  Float: number;
};

export type AddRatingInput = {
  beerId: Scalars['ID'];
  comment: Scalars['String'];
  stars: Scalars['Int'];
  userId: Scalars['ID'];
};

/** Represents a postal address, used to find Shops that sell `Beer` */
export type Address = {
  __typename?: 'Address';
  /** Name of the city */
  city: Scalars['String'];
  /** Country of this Address */
  country: Scalars['String'];
  /** Postal code */
  postalCode: Scalars['String'];
  /** The street */
  street: Scalars['String'];
};

export type Authentication = {
  __typename?: 'Authentication';
  authToken: Scalars['String'];
  userId: Scalars['String'];
  username: Scalars['String'];
};

/** Representation of a Beer that has been rated */
export type Beer = {
  __typename?: 'Beer';
  /** Average Rating of this Beer */
  averageStars: Scalars['Int'];
  /** Unique, immutable Id, that identifies this Beer */
  id: Scalars['ID'];
  /** The name of the beer */
  name: Scalars['String'];
  /** The Beer's price */
  price: Scalars['String'];
  /** List of all Ratings for this Beer */
  ratings: Array<Rating>;
  /** List of Ratings that has at exactly 'stars' Stars */
  ratingsWithStars: Array<Rating>;
  shops: Array<Shop>;
};


/** Representation of a Beer that has been rated */
export type BeerRatingsWithStarsArgs = {
  stars: Scalars['Int'];
};

export type LoginResponse = {
  __typename?: 'LoginResponse';
  authentication?: Maybe<Authentication>;
  error?: Maybe<Scalars['String']>;
};

export type Mutation = {
  __typename?: 'Mutation';
  /** Add a new `Rating` to a `Beer` and returns the new created `Rating` */
  addRating: Rating;
  login: LoginResponse;
  /**
   * Change the name of this `Beer`.
   *
   * Note: this is only allowed for admins (currently user 'U5' / 'nils')
   */
  updateBeerName: Beer;
};


export type MutationAddRatingArgs = {
  ratingInput?: InputMaybe<AddRatingInput>;
};


export type MutationLoginArgs = {
  password: Scalars['String'];
  username: Scalars['String'];
};


export type MutationUpdateBeerNameArgs = {
  beerId: Scalars['ID'];
  newName: Scalars['String'];
};

export type Query = {
  __typename?: 'Query';
  /** Returns a specific `Beer`, identified by its `id`, or `null` if not found */
  beer?: Maybe<Beer>;
  /** Returns a list of all `Beer` objects that we have in our system */
  beers: Array<Beer>;
  ping: Scalars['String'];
  /**
   * Returns a single `Shop`, identified by its `id`.
   *
   * - If there is no shop with the specified `id`, then `null` is returned
   */
  shop?: Maybe<Shop>;
  /** Returns a list of all Shops that are part of our network */
  shops: Array<Shop>;
};


export type QueryBeerArgs = {
  beerId: Scalars['ID'];
};


export type QueryPingArgs = {
  msg?: InputMaybe<Scalars['String']>;
};


export type QueryShopArgs = {
  shopId: Scalars['ID'];
};

/** A Rating that has been written by an Author for a Beer */
export type Rating = {
  __typename?: 'Rating';
  /** Who has written this Rating? */
  author: User;
  /** The  beer, this Rating is written for */
  beer: Beer;
  /** A comment for this beer */
  comment: Scalars['String'];
  /** An immutable unique Id */
  id: Scalars['ID'];
  /** Number of stars given with this rating */
  stars: Scalars['Int'];
};

/** A registered Shop that sells Beer */
export type Shop = {
  __typename?: 'Shop';
  /** Address of the shop */
  address: Address;
  /** All Beers this shop sells */
  beers: Array<Beer>;
  /** Unique ID of this shop */
  id: Scalars['ID'];
  /** The name of the shop */
  name: Scalars['String'];
};

export type Subscription = {
  __typename?: 'Subscription';
  newRatings: Rating;
  onNewRating: Rating;
};


export type SubscriptionNewRatingsArgs = {
  beerId: Scalars['ID'];
};

/** A User in our system that is allowed to leave Ratings */
export type User = {
  __typename?: 'User';
  id: Scalars['ID'];
  login: Scalars['String'];
  name: Scalars['String'];
};

export type BeerRatingsFragment = (
  { __typename?: 'Beer' }
  & Pick<Beer, 'id'>
  & { ratings: Array<(
    { __typename?: 'Rating' }
    & Pick<Rating, 'id'>
  )> }
);

export type AddRatingMutationVariables = Exact<{
  input: AddRatingInput;
}>;


export type AddRatingMutation = (
  { __typename?: 'Mutation' }
  & { addRating: (
    { __typename?: 'Rating' }
    & Pick<Rating, 'id' | 'comment' | 'stars'>
    & { beer: (
      { __typename?: 'Beer' }
      & Pick<Beer, 'id'>
    ), author: (
      { __typename?: 'User' }
      & Pick<User, 'name'>
    ) }
  ) }
);

export type NewRatingSubscriptionVariables = Exact<{
  beerId: Scalars['ID'];
}>;


export type NewRatingSubscription = (
  { __typename?: 'Subscription' }
  & { rating: (
    { __typename?: 'Rating' }
    & Pick<Rating, 'id' | 'stars' | 'comment'>
    & { beer: (
      { __typename?: 'Beer' }
      & Pick<Beer, 'id'>
    ), author: (
      { __typename?: 'User' }
      & Pick<User, 'name'>
    ) }
  ) }
);

export type SingleBeerFragment = (
  { __typename?: 'Beer' }
  & Pick<Beer, 'id' | 'name' | 'price'>
  & { ratings: Array<(
    { __typename?: 'Rating' }
    & Pick<Rating, 'id' | 'stars' | 'comment'>
    & { beer: (
      { __typename?: 'Beer' }
      & Pick<Beer, 'id'>
    ), author: (
      { __typename?: 'User' }
      & Pick<User, 'name'>
    ) }
  )>, shops: Array<(
    { __typename?: 'Shop' }
    & Pick<Shop, 'id' | 'name'>
  )> }
);

export type BeerPageQueryVariables = Exact<{
  beerId: Scalars['ID'];
}>;


export type BeerPageQuery = (
  { __typename?: 'Query' }
  & { beer?: Maybe<(
    { __typename?: 'Beer' }
    & Pick<Beer, 'id' | 'name' | 'price'>
    & { ratings: Array<(
      { __typename?: 'Rating' }
      & Pick<Rating, 'id' | 'stars' | 'comment'>
      & { beer: (
        { __typename?: 'Beer' }
        & Pick<Beer, 'id'>
      ), author: (
        { __typename?: 'User' }
        & Pick<User, 'name'>
      ) }
    )>, shops: Array<(
      { __typename?: 'Shop' }
      & Pick<Shop, 'id' | 'name'>
    )> }
  )> }
);

export type UpdateBeerNameMutationVariables = Exact<{
  beerId: Scalars['ID'];
  newName: Scalars['String'];
}>;


export type UpdateBeerNameMutation = (
  { __typename?: 'Mutation' }
  & { updatedBeer: (
    { __typename?: 'Beer' }
    & Pick<Beer, 'id' | 'name'>
  ) }
);

export type LoginMutationVariables = Exact<{
  username: Scalars['String'];
  password: Scalars['String'];
}>;


export type LoginMutation = (
  { __typename?: 'Mutation' }
  & { login: (
    { __typename?: 'LoginResponse' }
    & Pick<LoginResponse, 'error'>
    & { authentication?: Maybe<(
      { __typename?: 'Authentication' }
      & Pick<Authentication, 'userId' | 'username' | 'authToken'>
    )> }
  ) }
);

export type OverviewPageQueryVariables = Exact<{ [key: string]: never; }>;


export type OverviewPageQuery = (
  { __typename?: 'Query' }
  & { beers: Array<(
    { __typename?: 'Beer' }
    & Pick<Beer, 'id' | 'name' | 'averageStars'>
  )> }
);

export type ShopQueryVariables = Exact<{
  shopId: Scalars['ID'];
}>;


export type ShopQuery = (
  { __typename?: 'Query' }
  & { shop?: Maybe<(
    { __typename?: 'Shop' }
    & Pick<Shop, 'name'>
    & { beers: Array<(
      { __typename?: 'Beer' }
      & Pick<Beer, 'name'>
    )> }
  )> }
);

export type ShopListQueryVariables = Exact<{ [key: string]: never; }>;


export type ShopListQuery = (
  { __typename?: 'Query' }
  & { shops: Array<(
    { __typename?: 'Shop' }
    & Pick<Shop, 'id' | 'name'>
    & { address: (
      { __typename?: 'Address' }
      & Pick<Address, 'city' | 'postalCode' | 'street'>
    ) }
  )> }
);

export type ShopFragment = (
  { __typename?: 'Shop' }
  & Pick<Shop, 'id' | 'name'>
  & { address: (
    { __typename?: 'Address' }
    & Pick<Address, 'street' | 'postalCode' | 'city' | 'country'>
  ), beers: Array<(
    { __typename?: 'Beer' }
    & Pick<Beer, 'id' | 'name'>
  )> }
);

export type ShopPageQueryVariables = Exact<{
  shopId: Scalars['ID'];
}>;


export type ShopPageQuery = (
  { __typename?: 'Query' }
  & { shop?: Maybe<(
    { __typename?: 'Shop' }
    & Pick<Shop, 'id' | 'name'>
    & { address: (
      { __typename?: 'Address' }
      & Pick<Address, 'street' | 'postalCode' | 'city' | 'country'>
    ), beers: Array<(
      { __typename?: 'Beer' }
      & Pick<Beer, 'id' | 'name'>
    )> }
  )> }
);

export const BeerRatingsFragmentDoc = gql`
    fragment BeerRatings on Beer {
  id
  ratings {
    id
  }
}
    `;
export const SingleBeerFragmentDoc = gql`
    fragment SingleBeer on Beer {
  id
  name
  price
  ratings {
    id
    stars
    beer {
      id
    }
    author {
      name
    }
    comment
  }
  shops {
    id
    name
  }
}
    `;
export const ShopFragmentDoc = gql`
    fragment Shop on Shop {
  id
  name
  address {
    street
    postalCode
    city
    country
  }
  beers {
    id
    name
  }
}
    `;
export const AddRatingDocument = gql`
    mutation AddRating($input: AddRatingInput!) {
  addRating(ratingInput: $input) {
    id
    beer {
      id
    }
    author {
      name
    }
    comment
    stars
  }
}
    `;
export type AddRatingMutationFn = Apollo.MutationFunction<AddRatingMutation, AddRatingMutationVariables>;

/**
 * __useAddRatingMutation__
 *
 * To run a mutation, you first call `useAddRatingMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useAddRatingMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [addRatingMutation, { data, loading, error }] = useAddRatingMutation({
 *   variables: {
 *      input: // value for 'input'
 *   },
 * });
 */
export function useAddRatingMutation(baseOptions?: Apollo.MutationHookOptions<AddRatingMutation, AddRatingMutationVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useMutation<AddRatingMutation, AddRatingMutationVariables>(AddRatingDocument, options);
      }
export type AddRatingMutationHookResult = ReturnType<typeof useAddRatingMutation>;
export type AddRatingMutationResult = Apollo.MutationResult<AddRatingMutation>;
export type AddRatingMutationOptions = Apollo.BaseMutationOptions<AddRatingMutation, AddRatingMutationVariables>;
export const NewRatingDocument = gql`
    subscription NewRating($beerId: ID!) {
  rating: newRatings(beerId: $beerId) {
    id
    stars
    beer {
      id
    }
    author {
      name
    }
    comment
  }
}
    `;

/**
 * __useNewRatingSubscription__
 *
 * To run a query within a React component, call `useNewRatingSubscription` and pass it any options that fit your needs.
 * When your component renders, `useNewRatingSubscription` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the subscription, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useNewRatingSubscription({
 *   variables: {
 *      beerId: // value for 'beerId'
 *   },
 * });
 */
export function useNewRatingSubscription(baseOptions: Apollo.SubscriptionHookOptions<NewRatingSubscription, NewRatingSubscriptionVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useSubscription<NewRatingSubscription, NewRatingSubscriptionVariables>(NewRatingDocument, options);
      }
export type NewRatingSubscriptionHookResult = ReturnType<typeof useNewRatingSubscription>;
export type NewRatingSubscriptionResult = Apollo.SubscriptionResult<NewRatingSubscription>;
export const BeerPageDocument = gql`
    query BeerPage($beerId: ID!) {
  beer(beerId: $beerId) {
    ...SingleBeer
  }
}
    ${SingleBeerFragmentDoc}`;

/**
 * __useBeerPageQuery__
 *
 * To run a query within a React component, call `useBeerPageQuery` and pass it any options that fit your needs.
 * When your component renders, `useBeerPageQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useBeerPageQuery({
 *   variables: {
 *      beerId: // value for 'beerId'
 *   },
 * });
 */
export function useBeerPageQuery(baseOptions: Apollo.QueryHookOptions<BeerPageQuery, BeerPageQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<BeerPageQuery, BeerPageQueryVariables>(BeerPageDocument, options);
      }
export function useBeerPageLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<BeerPageQuery, BeerPageQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<BeerPageQuery, BeerPageQueryVariables>(BeerPageDocument, options);
        }
export type BeerPageQueryHookResult = ReturnType<typeof useBeerPageQuery>;
export type BeerPageLazyQueryHookResult = ReturnType<typeof useBeerPageLazyQuery>;
export type BeerPageQueryResult = Apollo.QueryResult<BeerPageQuery, BeerPageQueryVariables>;
export const UpdateBeerNameDocument = gql`
    mutation UpdateBeerName($beerId: ID!, $newName: String!) {
  updatedBeer: updateBeerName(beerId: $beerId, newName: $newName) {
    id
    name
  }
}
    `;
export type UpdateBeerNameMutationFn = Apollo.MutationFunction<UpdateBeerNameMutation, UpdateBeerNameMutationVariables>;

/**
 * __useUpdateBeerNameMutation__
 *
 * To run a mutation, you first call `useUpdateBeerNameMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useUpdateBeerNameMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [updateBeerNameMutation, { data, loading, error }] = useUpdateBeerNameMutation({
 *   variables: {
 *      beerId: // value for 'beerId'
 *      newName: // value for 'newName'
 *   },
 * });
 */
export function useUpdateBeerNameMutation(baseOptions?: Apollo.MutationHookOptions<UpdateBeerNameMutation, UpdateBeerNameMutationVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useMutation<UpdateBeerNameMutation, UpdateBeerNameMutationVariables>(UpdateBeerNameDocument, options);
      }
export type UpdateBeerNameMutationHookResult = ReturnType<typeof useUpdateBeerNameMutation>;
export type UpdateBeerNameMutationResult = Apollo.MutationResult<UpdateBeerNameMutation>;
export type UpdateBeerNameMutationOptions = Apollo.BaseMutationOptions<UpdateBeerNameMutation, UpdateBeerNameMutationVariables>;
export const LoginDocument = gql`
    mutation Login($username: String!, $password: String!) {
  login(username: $username, password: $password) {
    authentication {
      userId
      username
      authToken
    }
    error
  }
}
    `;
export type LoginMutationFn = Apollo.MutationFunction<LoginMutation, LoginMutationVariables>;

/**
 * __useLoginMutation__
 *
 * To run a mutation, you first call `useLoginMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useLoginMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [loginMutation, { data, loading, error }] = useLoginMutation({
 *   variables: {
 *      username: // value for 'username'
 *      password: // value for 'password'
 *   },
 * });
 */
export function useLoginMutation(baseOptions?: Apollo.MutationHookOptions<LoginMutation, LoginMutationVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useMutation<LoginMutation, LoginMutationVariables>(LoginDocument, options);
      }
export type LoginMutationHookResult = ReturnType<typeof useLoginMutation>;
export type LoginMutationResult = Apollo.MutationResult<LoginMutation>;
export type LoginMutationOptions = Apollo.BaseMutationOptions<LoginMutation, LoginMutationVariables>;
export const OverviewPageDocument = gql`
    query OverviewPage {
  beers {
    id
    name
    averageStars
  }
}
    `;

/**
 * __useOverviewPageQuery__
 *
 * To run a query within a React component, call `useOverviewPageQuery` and pass it any options that fit your needs.
 * When your component renders, `useOverviewPageQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useOverviewPageQuery({
 *   variables: {
 *   },
 * });
 */
export function useOverviewPageQuery(baseOptions?: Apollo.QueryHookOptions<OverviewPageQuery, OverviewPageQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<OverviewPageQuery, OverviewPageQueryVariables>(OverviewPageDocument, options);
      }
export function useOverviewPageLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<OverviewPageQuery, OverviewPageQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<OverviewPageQuery, OverviewPageQueryVariables>(OverviewPageDocument, options);
        }
export type OverviewPageQueryHookResult = ReturnType<typeof useOverviewPageQuery>;
export type OverviewPageLazyQueryHookResult = ReturnType<typeof useOverviewPageLazyQuery>;
export type OverviewPageQueryResult = Apollo.QueryResult<OverviewPageQuery, OverviewPageQueryVariables>;
export const ShopDocument = gql`
    query Shop($shopId: ID!) {
  shop(shopId: $shopId) {
    name
    beers {
      name
    }
  }
}
    `;

/**
 * __useShopQuery__
 *
 * To run a query within a React component, call `useShopQuery` and pass it any options that fit your needs.
 * When your component renders, `useShopQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useShopQuery({
 *   variables: {
 *      shopId: // value for 'shopId'
 *   },
 * });
 */
export function useShopQuery(baseOptions: Apollo.QueryHookOptions<ShopQuery, ShopQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<ShopQuery, ShopQueryVariables>(ShopDocument, options);
      }
export function useShopLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<ShopQuery, ShopQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<ShopQuery, ShopQueryVariables>(ShopDocument, options);
        }
export type ShopQueryHookResult = ReturnType<typeof useShopQuery>;
export type ShopLazyQueryHookResult = ReturnType<typeof useShopLazyQuery>;
export type ShopQueryResult = Apollo.QueryResult<ShopQuery, ShopQueryVariables>;
export const ShopListDocument = gql`
    query ShopList {
  shops {
    id
    name
    address {
      city
      postalCode
      street
    }
  }
}
    `;

/**
 * __useShopListQuery__
 *
 * To run a query within a React component, call `useShopListQuery` and pass it any options that fit your needs.
 * When your component renders, `useShopListQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useShopListQuery({
 *   variables: {
 *   },
 * });
 */
export function useShopListQuery(baseOptions?: Apollo.QueryHookOptions<ShopListQuery, ShopListQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<ShopListQuery, ShopListQueryVariables>(ShopListDocument, options);
      }
export function useShopListLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<ShopListQuery, ShopListQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<ShopListQuery, ShopListQueryVariables>(ShopListDocument, options);
        }
export type ShopListQueryHookResult = ReturnType<typeof useShopListQuery>;
export type ShopListLazyQueryHookResult = ReturnType<typeof useShopListLazyQuery>;
export type ShopListQueryResult = Apollo.QueryResult<ShopListQuery, ShopListQueryVariables>;
export const ShopPageDocument = gql`
    query ShopPage($shopId: ID!) {
  shop(shopId: $shopId) {
    ...Shop
  }
}
    ${ShopFragmentDoc}`;

/**
 * __useShopPageQuery__
 *
 * To run a query within a React component, call `useShopPageQuery` and pass it any options that fit your needs.
 * When your component renders, `useShopPageQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useShopPageQuery({
 *   variables: {
 *      shopId: // value for 'shopId'
 *   },
 * });
 */
export function useShopPageQuery(baseOptions: Apollo.QueryHookOptions<ShopPageQuery, ShopPageQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<ShopPageQuery, ShopPageQueryVariables>(ShopPageDocument, options);
      }
export function useShopPageLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<ShopPageQuery, ShopPageQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<ShopPageQuery, ShopPageQueryVariables>(ShopPageDocument, options);
        }
export type ShopPageQueryHookResult = ReturnType<typeof useShopPageQuery>;
export type ShopPageLazyQueryHookResult = ReturnType<typeof useShopPageLazyQuery>;
export type ShopPageQueryResult = Apollo.QueryResult<ShopPageQuery, ShopPageQueryVariables>;