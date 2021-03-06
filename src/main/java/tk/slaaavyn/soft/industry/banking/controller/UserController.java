package tk.slaaavyn.soft.industry.banking.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tk.slaaavyn.soft.industry.banking.config.EndpointConstants;
import tk.slaaavyn.soft.industry.banking.dto.user.CreateUserRequestDto;
import tk.slaaavyn.soft.industry.banking.dto.user.CustomerResponseDto;
import tk.slaaavyn.soft.industry.banking.dto.user.UserResponseDto;
import tk.slaaavyn.soft.industry.banking.exceptions.ApiRequestException;
import tk.slaaavyn.soft.industry.banking.model.User;
import tk.slaaavyn.soft.industry.banking.security.jwt.JwtUser;
import tk.slaaavyn.soft.industry.banking.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = EndpointConstants.USER_ENDPOINT)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    @ApiOperation(value = "Create new USER or ADMIN. Action as role: ADMIN")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "string", paramType = "header")
    protected ResponseEntity<UserResponseDto> create(@Valid @RequestBody CreateUserRequestDto userDto) {
        User createdUser = userService.create(userDto.fromDto());

        if(createdUser == null) {
            throw new ApiRequestException("user not created");
        }

        return ResponseEntity.ok(UserResponseDto.toDto(createdUser));
    }

    @GetMapping("/admins")
    @ApiOperation(value = "Getting all users with ROLE_ADMIN. Action as role: ADMIN")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "string", paramType = "header")
    protected ResponseEntity<List<UserResponseDto>> getAllAdmins() {
        List<UserResponseDto> userResponseDtoList = convertToDtoList(userService.getAllAdmins());
        return ResponseEntity.ok(userResponseDtoList);
    }

    @GetMapping("/customers")
    @ApiOperation(value = "Getting all users with ROLE_USER. Action as role: ADMIN")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "string", paramType = "header")
    protected ResponseEntity<List<UserResponseDto>> getAllCustomers() {
        List<UserResponseDto> userResponseDtoList = convertToDtoList(userService.getAllCustomers());
        return ResponseEntity.ok(userResponseDtoList);
    }

    @GetMapping("{id}")
    @ApiOperation(value = "Getting user with any role by userId. Action as role: ADMIN")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "string", paramType = "header")
    protected ResponseEntity<UserResponseDto> getUser(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(UserResponseDto.toDto(userService.getUser(id)));
    }

    @GetMapping("/customer-info")
    @ApiOperation(value = "Returns full information about the user who sent the request. Action as role: USER")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "string", paramType = "header")
    protected ResponseEntity<UserResponseDto> getUser() {
        JwtUser jwtUser = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return ResponseEntity.ok(CustomerResponseDto.toDto(userService.getUser(jwtUser.getId())));
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "Delete user by id. Action as role: ADMIN")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "string", paramType = "header")
    protected ResponseEntity<Object> delete(@PathVariable(name = "id") long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    private List<UserResponseDto> convertToDtoList(List<User> userList) {
        List<UserResponseDto> dtoList = new ArrayList<>();

        userList.forEach(user -> dtoList.add(UserResponseDto.toDto(user)));

        return dtoList;
    }
}
