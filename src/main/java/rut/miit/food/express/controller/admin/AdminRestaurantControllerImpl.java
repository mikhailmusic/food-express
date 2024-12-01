package rut.miit.food.express.controller.admin;

import food.express.contracts.controller.admin.AdminRestaurantController;
import food.express.contracts.form.DishCreateForm;
import food.express.contracts.form.RestaurantCreateForm;
import food.express.contracts.form.RestaurantEditForm;
import food.express.contracts.viewmodel.CreateViewModel;
import food.express.contracts.viewmodel.EditViewModel;
import food.express.contracts.viewmodel.category.CategoryViewModel;
import food.express.contracts.viewmodel.dish.DishInputViewModel;
import food.express.contracts.viewmodel.order.OrderItemViewModel;
import food.express.contracts.viewmodel.order.OrderRestaurantListViewModel;
import food.express.contracts.viewmodel.order.OrderRestaurantViewModel;
import food.express.contracts.viewmodel.restaurant.RestaurantViewModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rut.miit.food.express.controller.BaseControllerImpl;
import rut.miit.food.express.dto.dish.DishAddDto;
import rut.miit.food.express.dto.order.OrderDto;
import rut.miit.food.express.dto.restaurant.RestaurantAddDto;
import rut.miit.food.express.dto.restaurant.RestaurantDto;
import rut.miit.food.express.service.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/restaurants")
public class AdminRestaurantControllerImpl extends BaseControllerImpl implements AdminRestaurantController {
    private RestaurantService restaurantService;
    private DishCategoryService categoryService;
    private DishService dishService;
    private OrderDomainService orderService;

    @Autowired
    public void setRestaurantService(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Autowired
    public void setCategoryService(DishCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setDishService(DishService dishService) {
        this.dishService = dishService;
    }

    @Autowired
    public void setOrderService(OrderDomainService orderService) {
        this.orderService = orderService;
    }

    @Override
    @PostMapping("/add")
    public String addRestaurant(@Valid @ModelAttribute("form") RestaurantCreateForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            CreateViewModel viewModel = new CreateViewModel(
                    createBaseViewModel("Добавление ресторана")
            );
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "restaurant-add";
        }
        RestaurantAddDto dto = new RestaurantAddDto(form.name(), form.address(), form.description(), form.phoneNumber(),
                form.openTime(), form.closeTime(), form.minOrderAmount());
        Integer id = restaurantService.registerRestaurant(dto);
        return "redirect:/restaurants/" + id;
    }

    @Override
    @PostMapping("/{id}/edit")
    public String editRestaurant(@PathVariable Integer id, @Valid @ModelAttribute("form") RestaurantEditForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            EditViewModel viewModel = new EditViewModel(
                    createBaseViewModel("Редактирование информации")
            );
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "restaurant-edit";
        }
        RestaurantDto dto = new RestaurantDto(id, form.name(), form.address(), form.description(), form.phoneNumber(), form.openTime(), form.closeTime(), form.minOrderAmount());
        restaurantService.changeRestaurantInfo(dto);
        return "redirect:/restaurants/" + id;
    }

    @Override
    @GetMapping("/{id}/edit")
    public String editRestaurant(@PathVariable Integer id, Model model) {
        RestaurantDto dto = restaurantService.getRestaurantDetails(id);
        EditViewModel viewModel = new EditViewModel(
                createBaseViewModel("Редактирование информации")
        );
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new RestaurantEditForm(dto.id(), dto.name(), dto.address(), dto.description(), dto.phoneNumber(), dto.openTime(), dto.closeTime(), dto.minOrderAmount()));
        return "restaurant-edit";
    }

    @Override
    @GetMapping("/add")
    public String addRestaurant(Model model) {
        CreateViewModel viewModel = new CreateViewModel(
                createBaseViewModel("Добавление ресторана")
        );
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new RestaurantCreateForm("", "", "", "", null, null, BigDecimal.ZERO));
        return "restaurant-add";
    }

    @Override
    @GetMapping("/{id}/orders")
    public String listOrdersForRestaurant(@PathVariable Integer id, Model model) {
        List<OrderRestaurantViewModel> orderViewModels = new ArrayList<>();

        for (OrderDto dto : orderService.restaurantOrders(id)) {
            List<OrderItemViewModel> itemViewModels = dto.orderItems()
                    .stream().map(itemDto -> new OrderItemViewModel(itemDto.id(), itemDto.dishId(), itemDto.count(),
                            itemDto.dishName(), itemDto.imageURL())).toList();
            OrderRestaurantViewModel orderViewModel = new OrderRestaurantViewModel(dto.id(), dto.creationTime(),
                    dto.status().name(), itemViewModels);
            orderViewModels.add(orderViewModel);
        }
        OrderRestaurantListViewModel viewModel = new OrderRestaurantListViewModel(
                createBaseViewModel("История заказов"), orderViewModels
        );
        model.addAttribute("model", viewModel);
        return "restaurant-orders";
    }

    @Override
    @GetMapping("/{restaurantId}/add-dish")
    public String addDish(@PathVariable Integer restaurantId, Model model) {
        List<CategoryViewModel> categories = categoryService.getAllCategories()
                .stream().map(dto -> new CategoryViewModel(dto.id(), dto.name())).toList();
        DishInputViewModel viewModel = new DishInputViewModel(
                createBaseViewModel("Добавление блюда"), categories
        );
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new DishCreateForm("", "", BigDecimal.ONE, 1, 1, "", null, restaurantId));
        return "dish-add";
    }

    @Override
    @PostMapping("/{restaurantId}/add-dish")
    public String addDish(@PathVariable Integer restaurantId, @Valid @ModelAttribute("form") DishCreateForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<CategoryViewModel> categories = categoryService.getAllCategories()
                    .stream().map(dto -> new CategoryViewModel(dto.id(), dto.name())).toList();
            DishInputViewModel viewModel = new DishInputViewModel(
                    createBaseViewModel("Добавление блюда"), categories
            );
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "dish-add";
        }
        DishAddDto dto = new DishAddDto(form.name(), form.description(), form.price(), form.weight(),
                form.calories(), form.imageURL(), form.restaurantId(), form.categoryId());
        dishService.addDish(dto);
        return "redirect:/restaurants/" + restaurantId;
    }

}