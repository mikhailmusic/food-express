package rut.miit.food.express.controller.admin;

import food.express.contracts.controller.admin.AdminOrderController;
import food.express.contracts.viewmodel.order.OrderItemViewModel;
import food.express.contracts.viewmodel.order.OrderRestaurantListViewModel;
import food.express.contracts.viewmodel.order.OrderRestaurantViewModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rut.miit.food.express.controller.BaseControllerImpl;
import rut.miit.food.express.dto.order.OrderDto;
import rut.miit.food.express.service.OrderService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderControllerImpl extends BaseControllerImpl implements AdminOrderController {
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    @GetMapping("/restaurant/{id}")
    public String listOrdersForRestaurant(@PathVariable Integer id, Model model, Principal principal) {
        LOG.info("User '{}' is viewing orders for restaurant ID: {}", principal.getName(), id);

        List<OrderRestaurantViewModel> orderViewModels = new ArrayList<>();
        for (OrderDto dto : orderService.restaurantOrders(id)) {
            List<OrderItemViewModel> itemViewModels = dto.orderItems()
                    .stream().map(itemDto -> new OrderItemViewModel(itemDto.id(), itemDto.dishId(), itemDto.count(),
                            itemDto.dishName(), itemDto.imageURL(), itemDto.isVisible())).toList();
            OrderRestaurantViewModel orderViewModel = new OrderRestaurantViewModel(dto.id(), dto.creationTime(),
                    dto.status().name(), itemViewModels);
            orderViewModels.add(orderViewModel);
        }
        OrderRestaurantListViewModel viewModel = new OrderRestaurantListViewModel(
                createBaseViewModel("Заказы"), orderViewModels
        );
        model.addAttribute("model", viewModel);
        return "orders-restaurant";
    }

    @Override
    @PostMapping("/{id}/edit-status")
    public String editOrderStatus(@PathVariable Integer id, Principal principal) {
        LOG.info("User '{}' is changing the status for order ID: {}", principal.getName(), id);
        orderService.changeStatus(id);
        Integer restaurantId = orderService.getOrderDetails(id).restaurantId();
        LOG.info("Order ID: {} status successfully changed. Redirecting to restaurant ID: {}", id, restaurantId);
        return "redirect:/admin/orders/restaurant/" + restaurantId;
    }
}
