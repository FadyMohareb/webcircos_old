/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var converter = new Showdown.converter();
//var loginReact = require('./loginReact.js').loginReact;

var App = React.createClass({
  displayName: "App",
  getInitialState: function getInitialState() {
    return { view: { showModal: false } };
  },
  handleHideModal: function handleHideModal() {
    this.setState({ view: { showModal: false } });
    //Temporary fix
    $( ".modal-backdrop.in" ).remove();
  },
  handleShowModal: function handleShowModal() {
    this.setState({ view: { showModal: true } });
  },
  render: function render() {
    return React.createElement(
      "div",
      { className: "row" },
      React.createElement(
        "button",
        { className: "btn btn-default btn-block", onClick: this.handleShowModal },
        "Open Modal"
      ),
      this.state.view.showModal ? React.createElement(SigninForm, { handleHideModal: this.handleHideModal }) : null
    );
  }
});

var renderJoinUS = function () {
    React.render(React.createElement(App, null), document.getElementById('container'));
};