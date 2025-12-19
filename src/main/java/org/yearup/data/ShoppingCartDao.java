package org.yearup.data;

import org.yearup.models.ShoppingCart;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    // add additional method signatures here
    void addToCart(int productId, int userId);

    void clearCart(int userId);

    public void editCart(int productId, int userId, int quantity);
}
