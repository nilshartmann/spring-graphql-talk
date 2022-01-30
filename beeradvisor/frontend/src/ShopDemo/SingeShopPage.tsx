import { useParams } from "react-router-dom";

export default function SingeShopPage() {
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const { shopId } = useParams<{ shopId: string }>();

  return (
    <div>
      <h1>Shop</h1>
      <p>Todo: show Shop!</p>
    </div>
  );
}
