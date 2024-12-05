package rut.miit.food.express.controller.admin;

import food.express.contracts.controller.admin.AdminOrderController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rut.miit.food.express.service.OrderDomainService;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderControllerImpl implements AdminOrderController {
    private OrderDomainService orderService;

    @Autowired
    public void setOrderService(OrderDomainService orderService) {
        this.orderService = orderService;
    }

    @Override
    @GetMapping("/{id}/edit-status")
    public String editOrderStatus(@PathVariable Integer id) {
        orderService.changeStatus(id);
        Integer restaurantId = orderService.getOrderDetails(id).restaurantId();
        return "redirect:/admin/restaurants/" + restaurantId + "/orders";
    }
}
