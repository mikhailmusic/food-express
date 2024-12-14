package food.express.contracts.controller.admin;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/orders")
public interface AdminOrderController {

    @GetMapping("/{id}/edit-status")
    String editOrderStatus(@PathVariable Integer id);

    @GetMapping("/restaurant/{id}")
    String listOrdersForRestaurant(@PathVariable Integer id, Model model);
}
