interface ShoppingCart {
  id: string;
  totalPrice: number;
  shoppingCartItems: ShoppingCartItem[];
  userId: string;
  dateCreate?: any;
  dateChange?: any;
  changedBy?: any;
  deleted: boolean;
}
interface ShoppingCartItem {
  id: string;
  cartItemName: string;
  quantity?: any;
  imageUrl: string;
  cartItemPrice: number;
  shoppingCartId: string;
  courseId: string;
  dateCreate: string;
  dateChange: string;
  changedBy: string;
  deleted: boolean;
}
