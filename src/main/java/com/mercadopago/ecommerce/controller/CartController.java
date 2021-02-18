package com.mercadopago.ecommerce.controller;

import com.google.gson.GsonBuilder;
import com.mercadopago.MercadoPago;
import com.mercadopago.ecommerce.domain.model.Cart;
import com.mercadopago.ecommerce.domain.service.CartService;
import com.mercadopago.ecommerce.exception.ResourceNotFoundException;
import com.mercadopago.ecommerce.resource.CartResource;
import com.mercadopago.exceptions.MPConfException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.Item;
import com.mercadopago.resources.datastructures.preference.Payer;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name="cart", description = "cart API")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class CartController {

    @Autowired
    private ModelMapper mapper  ;

    @Autowired
    private CartService cartService;

    @GetMapping("/cart")
    public Page<CartResource> getAllCart(@Parameter(description = "Pageable parameter")  Pageable pageable){
        Page<Cart> cartPage = cartService.getAllCarts(pageable);
        List<CartResource> cartResourceList = cartPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<CartResource>(cartResourceList, pageable, cartResourceList.size());
    }

    @PostMapping("/cart/{cartId}/products")
    public CartResource addProduct(@PathVariable(name="cartId") Long cartId, @PathVariable(name="productId") Long productId){
        return convertToResource(cartService.addProductsToCart(cartId, productId));
    }

    @PostMapping("/cart/checkout")
    public ResponseEntity testing(){
        try{
            MercadoPago.SDK.setAccessToken("APP_USR-5926442828997072-021405-9af87e32da2cafbf2b881324d48a1796-715317686");
        }catch (MPConfException e){
            e.printStackTrace();
        }
        try{
            Preference preference = new Preference();
            // Create a preference item
            Item item =  new Item();
            item.setTitle("Mi producto")
                    .setId("1234")
                    .setQuantity(1)
                    .setDescription("Producto de prueba")
                    .setPictureUrl("https://s3.amazonaws.com/wordpress-media-s3/wp-content/uploads/2019/12/02095839/int-17-16.jpg")
                    .setUnitPrice((float)1.00);
            Payer payer = new Payer();
            payer.setName("Hello world");
            preference.setPayer(payer);
            preference.appendItem(item);
            preference.setExternalReference("urielkillian2607@hotmail.com");
            preference.setNotificationUrl("https://ecommerce-mercado-pago-backend.herokuapp.com/api/cart/notifications");
            preference = preference.save();
            return ResponseEntity.ok(preference.save().getInitPoint());
        } catch (MPException e){
            throw new ResourceNotFoundException("Something was wrong", e);
        }
    }

    @GetMapping("/cart/success")
    public String success(HttpServletRequest request,
                          @RequestParam("collection_id")String collectionId,
                          @RequestParam("collection_status")String collectionStatus,
                          @RequestParam("external_reference")String externalReference,
                          @RequestParam("payment_type")String paymentType,
                          @RequestParam("merchant_order_id")String merchantOrderId,
                          @RequestParam("preference_id")String preferenceId,
                          @RequestParam("site_id")String siteId,
                          @RequestParam("processing_node")String proccessingNode,
                          @RequestParam("merchant_account_id") String merchantAccountId,
                          Model model
    ) throws MPException {
        var payment = com.mercadopago.resources.Payment.findById(collectionId);
        System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(payment));

        model.addAttribute("payment", payment);
        return "ok";
    }

    @PostMapping("/cart/notifications")
    public ResponseEntity<?>paymentNotification(@RequestParam(required = false) Long id, @RequestParam(name = "data.id", required = false) Long dataId, @RequestParam(required = false) String type, @RequestParam(required = false) String topic, @RequestBody Map<String, Object> requestBody){
        if(id != null && topic != null){
            System.out.println("============= RECEIVED PIN =============");
            System.out.println("Id: " + id);
            System.out.println("Topic: " + topic);
            System.out.println(requestBody);
            System.out.println("=========================================");
        } else if (dataId != null && type != null){
            System.out.println("============== NEW WEB HOOK =============");
            System.out.println("Data Id: " + dataId);
            System.out.println("Topic: " + type);
            System.out.println(requestBody);
            System.out.println("==========================================");
        } else{
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(200);
    }

    private CartResource convertToResource(Cart entity){
        return mapper.map(entity, CartResource.class);
    }
}
