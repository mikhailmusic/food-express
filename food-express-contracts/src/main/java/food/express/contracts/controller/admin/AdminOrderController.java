package food.express.contracts.controller.admin;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/admin/orders")
public interface AdminOrderController {

    @PostMapping("/{id}/edit-status")
    String editOrderStatus(@PathVariable Integer id, Principal principal);

    @GetMapping("/restaurant/{id}")
    String listOrdersForRestaurant(@PathVariable Integer id, Model model, Principal principal);
}
