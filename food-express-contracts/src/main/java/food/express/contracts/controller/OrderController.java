package food.express.contracts.controller;

import food.express.contracts.form.OrderItemEditForm;
import food.express.contracts.form.ReviewCreateForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/orders")
public interface OrderController extends BaseController {

    @GetMapping("/create/add-item/{dishId}")
    String addDishToOrder(Principal principal, @PathVariable Integer dishId, HttpServletRequest request);

    @PostMapping("/create/change-item")
    String editDishCount(@Valid @ModelAttribute("form") OrderItemEditForm form);

    @GetMapping("/create/{id}")
    String createOrder(@PathVariable Integer id);

    @GetMapping("/create/user")
    String createOrder(Principal principal, Model model);

    @PostMapping("/{id}")
    String cancelOrder(@PathVariable Integer id);


    @GetMapping("/user")
    String listOrders(Principal principal, Model model);

    @GetMapping("/{id}")
    String orderDetails(@PathVariable Integer id, Model model);

    @GetMapping("/{id}/add-review")
    String addReviewToOrder(@PathVariable Integer id, Model model);

    @PostMapping("/{id}/add-review")
    String addReviewToOrder(@PathVariable Integer id, @Valid @ModelAttribute("form") ReviewCreateForm form, BindingResult result, Model model);
}
