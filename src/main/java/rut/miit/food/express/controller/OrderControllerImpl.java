package rut.miit.food.express.controller;

import food.express.contracts.controller.OrderController;
import food.express.contracts.form.ReviewCreateForm;
import food.express.contracts.viewmodel.CreateViewModel;
import food.express.contracts.viewmodel.order.*;
import food.express.contracts.viewmodel.review.ReviewViewModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rut.miit.food.express.dto.order.OrderDto;
import rut.miit.food.express.dto.order.OrderItemUpdateDto;
import rut.miit.food.express.dto.review.ReviewAddDto;
import rut.miit.food.express.dto.review.ReviewDto;
import rut.miit.food.express.service.OrderDomainService;
import rut.miit.food.express.service.ReviewService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderControllerImpl extends BaseControllerImpl implements OrderController {
    private OrderDomainService orderService;
    private ReviewService reviewService;

    @Autowired
    public void setOrderService(OrderDomainService orderService) {
        this.orderService = orderService;
    }
    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Override
    @GetMapping("/add/add-item/{dishId}")
    public  String addDishToOrder(@PathVariable Integer dishId) {
        // TODO
        return "redirect:/restaurants";
    }

    @Override
    @PostMapping("/add/change-item")
    public String editDishCount(@RequestParam Integer itemId, @RequestParam Integer newCount) {
        // TODO
        OrderItemUpdateDto dto = new OrderItemUpdateDto(itemId, newCount);
        orderService.changeOrderItemToOrder(dto);
        return "redirect:/orders/add/user/1";

    }

    @Override
    @GetMapping("/add/{id}")
    public String createOrder(@PathVariable Integer id) {
        // TODO
        orderService.createOrder(id);
        return "redirect:/orders/" + id;

    }

    @Override
    @PostMapping("/{id}")
    public String cancelOrder(@PathVariable Integer id) {
        // TODO
        orderService.cancelOrder(id);
        return "";

    }

    @Override
    @GetMapping("/add/user/{userId}")
    public String createOrder(@PathVariable Integer userId, Model model) {
        List<OrderCreateViewModel> orderViewModels = new ArrayList<>();
        for (OrderDto dto : orderService.userOrdersDraft(userId)) {
            List<OrderItemViewModel> itemViewModels = dto.orderItems()
                    .stream().map(item -> new OrderItemViewModel(item.id(), item.dishId(), item.count(), item.dishName(), item.imageURL())).toList();
            orderViewModels.add(new OrderCreateViewModel(dto.id(), dto.restaurantId(),dto.restaurantName(), itemViewModels));
        }
        OrderCreateListViewModel viewModel = new OrderCreateListViewModel(
                createBaseViewModel("Заказ"), orderViewModels
        );

        model.addAttribute("model", viewModel);
        return "order-add";

    }

    @Override
    @GetMapping("/user/{id}")
    public String listOrders(@PathVariable Integer id, Model model) {
        List<OrderInfoViewModel> orderViewModels = new ArrayList<>();

        for (OrderDto dto : orderService.userOrdersHistory(id)){
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
        return "redirect:/orders/" + id;

    }


    private OrderInfoViewModel toViewModel(OrderDto dto) {
        List<OrderItemViewModel> itemViewModels = dto.orderItems()
                .stream().map(itemDto -> new OrderItemViewModel(itemDto.id(), itemDto.dishId(), itemDto.count(),
                        itemDto.dishName(), itemDto.imageURL())).toList();
        ReviewDto reviewDto = dto.reviewDto();
        ReviewViewModel reviewViewModel = null;
        if (reviewDto != null) {
            reviewViewModel = new ReviewViewModel(reviewDto.rating(), reviewDto.text(), reviewDto.date());
        }
        OrderInfoViewModel orderViewModel = new OrderInfoViewModel(dto.id(), dto.creationTime(), dto.deliveryTime(),
                dto.status().name(), dto.restaurantId(), dto.restaurantName(), itemViewModels, reviewViewModel);
        return orderViewModel;
    }

}
