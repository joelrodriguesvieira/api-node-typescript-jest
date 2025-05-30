import * as express from "express";
import { UserController } from "../controllers/user.controller";
import { authenticate } from "../middlewares/authenticate.middleware";

const Router = express.Router()

Router.post("/signup", UserController.create)
Router.post("/login", UserController.login)

Router.get(
    "/", 
    authenticate, 
    UserController.getUsers
)

Router.delete(
    "/delete/:id", 
    authenticate, 
    UserController.delete
)

Router.put(
    "/update/:id",
    authenticate,
    UserController.update
)

export { Router as userRouter }