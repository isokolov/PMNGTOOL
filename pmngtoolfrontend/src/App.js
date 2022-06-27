import React, { Component } from "react";
import "./App.css";
import Dashboard from "./components/Dashboard";
import Header from "./components/Layout/Header";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Route, Routes} from "react-router-dom";
import AddProject from "./components/Project/AddProject";


class App extends Component {
  render() {
    return (
      
      <Router>
        <div className="App">
          <Header />
          <Routes>
            <Route  exact path="/addProject" element={<AddProject />} />
            <Route  exact path="/dashboard" element={<Dashboard />} />
         </Routes>         
          {/*<Route  path="/dashboard" component={Dashboard} />
          <Route  path="/addProject" component={AddProject} />*/}
        </div>
    </Router>
    
    );
  }
}

export default App;

 {/*<Router>
        <div className="App">
          <Header />
          <Dashboard />
          <Route exact path="/dashboard" component={Dashboard} />
          <Route exact path="/addProject" component={AddProject} />
        </div>
    </Router>*/}