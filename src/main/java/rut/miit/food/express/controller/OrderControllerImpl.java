package rut.miit.food.express.controller;

import food.express.contracts.controller.OrderController;
import food.express.contracts.form.OrderItemEditForm;
import food.express.contracts.form.ReviewCreateForm;
import food.express.contracts.viewmodel.CreateViewModel;
import food.express.contracts.viewmodel.order.*;
import food.express.contracts.viewmodel.review.ReviewViewModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rut.miit.food.express.dto.order.OrderDto;
import rut.miit.food.express.dto.order.OrderItemAddDto;
import rut.miit.food.express.dto.order.OrderItemUpdateDto;
import rut.miit.food.express.dto.review.ReviewAddDto;
import rut.miit.food.express.dto.review.ReviewDto;
import rut.miit.food.express.service.OrderService;
import rut.miit.food.express.service.ReviewService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderControllerImpl extends BaseControllerImpl implements OrderController {
    private OrderService orderService;
    private ReviewService reviewService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Override
    @GetMapping("/create/add-item/{dishId}")
    public  String addDishToOrder(Principal principal, @PathVariable Integer dishId, HttpServletRequest request) {
        OrderItemAddDto dto = new OrderItemAddDto(dishId, principal.getName(), 1);
        orderService.addOrderItemToOrder(dto);
        String refererUrl = request.getHeader("Referer");
        if (refererUrl != null) return "redirect:" + refererUrl;
        return "redirect:/restaurants";
    }

    @Override
    @PostMapping("/create/change-item")
    public String editDishCount(@Valid @ModelAttribute("form") OrderItemEditForm form) {
        OrderItemUpdateDto dto = new OrderItemUpdateDto(form.itemId(), form.count());
        orderService.changeOrderItemToOrder(dto);
        return "redirect:/orders/create/user";

    }

    @Override
    @GetMapping("/create/{id}")
    public String createOrder(@PathVariable Integer id) {
        orderService.createOrder(id);
        return "redirect:/orders/user";

    }

    @Override
    @PostMapping("/{id}")
    public String cancelOrder(@PathVariable Integer id) {
        orderService.cancelOrder(id);
        return "redirect:/orders/user";
    }

    @Override
    @GetMapping("/create/user")
    public String createOrder(Principal principal, Model model) {
        List<OrderCreateViewModel> orderViewModels = new ArrayList<>();
        for (OrderDto dto : orderService.userOrdersDraft(principal.getName())) {

            List<OrderItemViewModel> itemViewModels = dto.orderItems()
                    .stream().map(item -> new OrderItemViewModel(item.id(), item.dishId(), item.count(), item.dishName(), item.imageURL(), item.isVisible())).toList();
            orderViewModels.add(new OrderCreateViewModel(dto.id(), dto.restaurantId(),dto.restaurantName(), dto.totalAmount(), itemViewModels));
        }
        OrderCreateListViewModel viewModel = new OrderCreateListViewModel(
                createBaseViewModel("Корзина"), orderViewModels
        );
        model.addAttribute("model", viewModel);
        return "order-add";

    }

    @Override
    @GetMapping("/user")
    public String listOrders(Principal principal, Model model) {
        List<OrderInfoViewModel> orderViewModels = new ArrayList<>();

        for (OrderDto dto : orderService.userOrdersHistory(principal.getName())){
            orderViewModels.add(toViewModel(dto));
        }
        OrderInfoListViewModel viewModel = new OrderInfoListViewModel(
                createBaseViewModel("История заказов"), orderViewModels
        );
        model.addAttribute("model", viewModel);
        return "orders-user";

    }

    @Override
    @GetMapping("/{id}")
    public String orderDetails(@PathVariable Integer id, Model model) {
        OrderDto dto = orderService.getOrderDetails(id);

        OrderViewModel viewModel = new OrderViewModel(
                createBaseViewModel("Информация о заказе"), toViewModel(dto)
        );
        model.addAttribute("model", viewModel);
        return "order-details";

    }

    @Override
    @GetMapping("/{id}/add-review")
    public String addReviewToOrder(@PathVariable Integer id, Model model) {
        CreateViewModel viewModel = new CreateViewModel(
                createBaseViewModel("Добавление отзыва")
        );
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new ReviewCreateForm(5, "", id));
        return "review-add";

    }

    @Override
    @PostMapping("/{id}/add-review")
    public String addReviewToOrder(@PathVariable Integer id, @Valid @ModelAttribute("form") ReviewCreateForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            CreateViewModel viewModel = new CreateViewModel(
                    createBaseViewModel("Добавление отзыва")
            );
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "review-add";
        }
        ReviewAddDto dto = new ReviewAddDto(id, form.rating(), form.text());
        reviewService.leaveReview(dto);
        return "redirect:/orders/user";

    }


    private OrderInfoViewModel toViewModel(OrderDto dto) {
        List<OrderItemViewModel> itemViewModels = dto.orderItems()
                .stream().map(itemDto -> new OrderItemViewModel(itemDto.id(), itemDto.dishId(), itemDto.count(),
                        itemDto.dishName(), itemDto.imageURL(), itemDto.isVisible())).toList();
        ReviewDto reviewDto = dto.reviewDto();
        ReviewViewModel reviewViewModel = null;
        if (reviewDto != null) {
            reviewViewModel = new ReviewViewModel(reviewDto.rating(), reviewDto.text(), reviewDto.date(), reviewDto.userFirstName());
        }
        OrderInfoViewModel orderViewModel = new OrderInfoViewModel(dto.id(), dto.creationTime(), dto.deliveryTime(),
                dto.status().name(), dto.totalAmount(), dto.restaurantId(), dto.restaurantName(), itemViewModels, reviewViewModel);
        return orderViewModel;
    }

}
