import { useShopListQuery } from "../generated/graphql";

export default function ShopListPage() {
  const { data, loading, error } = useShopListQuery();

  if (loading) {
    return <h1>Loading...</h1>;
  }

  if (error) {
    return <h1>Error!</h1>;
  }

  if (!data) {
    return <h1>No data found</h1>;
  }

  return (
    <div>
      <h1>Shop List</h1>

      <ul>
        {data.shops.map((shop) => {
          return (
            <li>
              {shop.name} {shop.address.city}
            </li>
          );
        })}
      </ul>
    </div>
  );
}
