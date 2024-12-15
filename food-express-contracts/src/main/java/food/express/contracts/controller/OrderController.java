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

    @PostMapping("/create/add-item/{dishId}")
    String addDishToOrder(@PathVariable Integer dishId, Principal principal, HttpServletRequest request);

    @PostMapping("/create/change-item")
    String editDishCount(@Valid @ModelAttribute("form") OrderItemEditForm form, Principal principal);

    @PostMapping("/create/{id}")
    String createOrder(@PathVariable Integer id, Principal principal);

    @PostMapping("/cancel/{id}")
    String cancelOrder(@PathVariable Integer id, Principal principal);

    @GetMapping("/create")
    String createOrder(Principal principal, Model model);

    @GetMapping("/user")
    String listOrders(Principal principal, Model model);

    @GetMapping("/{id}")
    String orderDetails(@PathVariable Integer id, Model model, Principal principal);

    @GetMapping("/{id}/add-review")
    String addReviewToOrder(@PathVariable Integer id, Model model, Principal principal);

    @PostMapping("/{id}/add-review")
    String addReviewToOrder(@PathVariable Integer id, @Valid @ModelAttribute("form") ReviewCreateForm form,
                            Principal principal, BindingResult result, Model model);
}
