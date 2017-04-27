/* global Showdown, React, ChangePswdModal */

var converter = new Showdown.converter();

var AccountDropdown = React.createClass({displayName: "accountDropdown",
    handleSubmit: function (e) {
        e.preventDefault();
        $.ajax({
            url: "/logOutAction",
            type: 'GET',
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if(data.errors === null) 
                {   $.cookie('activeProject', "");
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
        return (React.createElement("div", { className: 'container'},
        React.createElement("div", { className: 'container'},
            React.createElement("h2", null, "Welcome ", React.createElement("strong", null, this.props.userName), "!")),
            React.createElement("div", { className: 'btn-group', style: {float: 'left'}},
                React.createElement("button", { type: 'button', className: "btn btn-primary dropdown-toggle", 
                    'data-toggle': "dropdown", 'aria-haspopup': "true", 'aria-expanded': "false"},React.createElement('strong', null, "Account "), 
                    React.createElement("span", { className: 'caret' }),
                    React.createElement("span", { className: 'sr-only' })),
                React.createElement("ul", { className: 'dropdown-menu' },
                React.createElement("li", { className: 'dropdown-header' }, "Manage account"), 
                    React.createElement("li", null, 
                        React.createElement("a", {href: "#", onClick: this.handleShowModal }, "Change password")),
                    React.createElement("li", { role: 'separator', className: 'divider'}),
                React.createElement("li", null,
                        React.createElement("a", {href: '#', onClick: this.handleSubmit },  "Log out "))
                 ),
                this.state.view.showModal ? React.createElement(ChangePswdModal, { handleHideModal: this.handleHideModal }) : null
                )
        ));
    }
});
var renderAccountDropdown = function (asd) {
//    console.log(asd);
    React.render(
        React.createElement(AccountDropdown, {userName: asd}),
        document.getElementById("upperLeftContainer")
    );
};

