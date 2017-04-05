/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var converter = new Showdown.converter();

var AccountDropdown = React.createClass({displayName: "accountDropdown",
     handleSubmit: function (e) {
        e.preventDefault();
        $.ajax({
            url: "/logOutAction",
            type: 'GET',
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if(data.errors == null) {
                    location = '/home';
                }else
                {
                    alert("Logout didn't work");
                }
            },
            error: function (xhr, status, err) {
                console.error(status, err.toString());
            }
        });
        return 
    },
    
  getInitialState: function getInitialState() {
    return { view: { showModal: false } };
  },
  handleHideModal: function handleHideModal() {
    this.setState({ view: { showModal: false } });
    $( ".modal-backdrop.in" ).remove();
  },
  handleShowModal: function handleShowModal() {
    this.setState({ view: { showModal: true } });
  },
    
    render: function () {
        return (
            React.createElement("div", { className: 'btn-group', style: { float: 'right', 'margin-top': '10px' } },
                React.createElement("button", { type: 'button', className: "btn btn-primary dropdown-toggle",
                    'data-toggle': "dropdown", 'aria-haspopup': "true", 'aria-expanded': "false"}, 
                    "Account  ", 
                    React.createElement("span", { className: 'caret' }),
                    React.createElement("span", { className: 'sr-only' })),
                React.createElement("ul", { className: 'dropdown-menu dropdown-menu-right' },
                React.createElement("li", { className: 'dropdown-header' }, "Manage account"), 
                    React.createElement("li", null, 
                        React.createElement("a", {href: "#", onClick: this.handleShowModal }, "Change password")),
                    React.createElement("li", { role: 'separator', className: 'divider'}),
                React.createElement("li", null,
                        React.createElement("a", {href: '#', onClick: this.handleSubmit },  "Log out "))
                 ),
                this.state.view.showModal ? React.createElement(ChangePswdModal, { handleHideModal: this.handleHideModal }) : null
                )
        );
    }
});

var renderAccountDropdown = function (asd) {
    console.log(asd)
    React.render(
        React.createElement(AccountDropdown),
        document.getElementById("upperRightContainer")
    );
};

