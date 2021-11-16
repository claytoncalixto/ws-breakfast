import Access from 'pages/Access';
import Dashboard from 'pages/Dashboard';
import Home from 'pages/Home';
import Register from 'pages/Register';
import { Switch } from "react-native";
import { BrowserRouter, Route  } from "react-router-dom";

const Routes = () => {
    return (
        <BrowserRouter>
            <Switch>
                <Route path="/" >
                  <Home />
                </Route>

                <Route path="/dashboard" >
                    <Dashboard />
                </Route>

                <Route path="/access" >
                    <Access />
                </Route>

                <Route path="/register" >
                    <Register />
                </Route>
            </Switch>
        </BrowserRouter>
    );
}

export default Routes;