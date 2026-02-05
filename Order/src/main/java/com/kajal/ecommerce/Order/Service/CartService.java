package com.kajal.ecommerce.Order.Service;

import com.kajal.ecommerce.Order.DTO.CartItemRequest;
import com.kajal.ecommerce.Order.DTO.ProductResponse;
import com.kajal.ecommerce.Order.DTO.UserResponse;
import com.kajal.ecommerce.Order.Repository.CartRepository;
//import com.app.ecom_application.Repository.ProductRepository;
//import com.app.ecom_application.Repository.UserRepository;
import com.kajal.ecommerce.Order.clients.ProductServiceClient;
import com.kajal.ecommerce.Order.clients.UserServiceClient;
import com.kajal.ecommerce.Order.entity.Cart;
//import jakarta.transaction.Transactional;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.Value;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class CartService
{
//    private ProductRepository productRepository;
    private CartRepository cartRepository;
//    private UserRepository userRepository;
    private final ProductServiceClient productServiceClient;
    private final UserServiceClient userServiceClient;

    @CircuitBreaker(name = "productService", fallbackMethod = "addToCartFallBack")
    public String addCart(String userId, CartItemRequest request)
    {
        //Look if product is present or not
//        Optional<Product> productOpt= productRepository.findById(request.getProductId());
//
//        if(productOpt.isEmpty())
//        {
//            return false;
//            return "Product not found";
//        }
        ProductResponse product= productServiceClient.getProductDetails(request.getProductId());

        if(product ==null)
        {
//            return false;
            return "Product not found";
        }
//
//        //Get the product now
//        Product product=productOpt.get();
//
//        //check for quantity
//        if(product.getStockQuantity()<request.getQuantity())
//        {
//              return false;
//            return "Product out of stock";
//        }
        //check for quantity
        if(product.getStockQuantity()<request.getQuantity())
        {
//            return false;
            return "Product out of stock";
        }
//
//        //check if user exists or not
//        Optional<User> userOpt=userRepository.findById(Long.valueOf(userId));
//
//        if(userOpt.isEmpty())
//        {
////            return false;
//            return "User not found";
//        }

        //get user

//        User user=userOpt.get();

        UserResponse user= userServiceClient.getUserDetails(userId);

        if(user ==null)
        {
//            return false;
            return "User not found";
        }

        //Now, adding item to cart......

        Cart existingCartItem = cartRepository.findByUserIdAndProductId(userId, request.getProductId());

        if(existingCartItem!=null)
        {
            //update the quantity
            existingCartItem.setQuantity(existingCartItem.getQuantity()+ request.getQuantity());
            existingCartItem.setPrice(BigDecimal.valueOf(1000.00));

            cartRepository.save(existingCartItem);

            return "Item already exists in the cart.Quantity updated....";
        }
        else
        {
            //create new cart item
            Cart cart=new Cart();

            cart.setUserId(userId);
            cart.setProductId(request.getProductId());
            cart.setQuantity(request.getQuantity());
            cart.setPrice(BigDecimal.valueOf(1000.00));

            cartRepository.save(cart);

            return "Item added to the cart";
        }

//        return true;
    }

    public String addToCartFallBack(String userId, CartItemRequest request, Exception exception)
    {

        exception.printStackTrace();
        return "Fallback called";
    }

    public boolean deleteItemFromCart(String userId, String productId)
    {
        //Look if product is present or not
//        Optional<Product> productOpt= productRepository.findById(productId);

//        if(productOpt.isEmpty())
//        {
//            return false;
//        }

        //check if user exists or not
//        Optional<User> userOpt=userRepository.findById(Long.valueOf(userId));

//        if(userOpt.isEmpty())
//        {
//            return false;
//        }

//        userOpt.flatMap(user -> productOpt.map(product -> {
//            cartRepository.deleteByUserAndProduct(user, product);
//            return true;
//        }));
//        return true;
        Cart existingCartItem=cartRepository.findByUserIdAndProductId(userId, productId);
        if(existingCartItem!=null)
        {
            cartRepository.delete(existingCartItem);
            return true;
        }

        return false;
    }

    public List<Cart> getAllItems(String userId)
    {
        return cartRepository.findByUserId(userId);
    }

    public void clearCart(String userId)
    {
        cartRepository.deleteByUserId(userId);
    }
}
