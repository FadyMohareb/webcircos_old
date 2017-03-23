var converter = new Showdown.converter();

var SignupForm = React.createClass({displayName: "RegistrationForm",

    componentDidMount: function componentDidMount(){
        $(this.getDOMNode()).modal('show');
        $(this.getDOMNode()).on('hidden.bs.modal', this.props.handleHideRegModal);
        
    },
    
    validateInput: function(user){
       if (user.login == "" || user.password == "" || user.name == "" || user.email == "")
        {
            alert("All fields are required!");
            return false;
        }
        
        return true;
    },
    
    handleSubmit: function (e) {
        e.preventDefault();
        var user = {};
        user.login = this.refs.login.getDOMNode().value.trim();
        user.password = this.refs.password.getDOMNode().value.trim();
        user.name = this.refs.name.getDOMNode().value.trim();
        user.email = this.refs.email.getDOMNode().value.trim();
        
        if(this.validateInput(user)){
            console.log(user);
            console.log(JSON.stringify(user))
            $.ajax({
                url: "/registrationReact",
                dataType: 'json',
                type: 'POST',
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(user),
                success: function (data) {
                    console.log(data)
                    if(data.errors == null) {
                        location = '/home';
                    }else
                    {
                        alert("Incorrect login/password");
                    }
                },
                error: function (xhr, status, err) {
                    alert("Wrong data");
                    console.error(status, err.toString());
                }
            });
        }
        return
    },
    
    render: function () {
        return (React.createElement('div',{ className: 'modal fade', id: "registrationModal" },
                    React.createElement('div',{ className: 'modal-dialog' },
                        React.createElement('div',{ className: 'modal-content' },
                            React.createElement('div',{ className: 'modal-header' },
                                React.createElement('button',{ type: 'button', className: 'close',
                                    'data-dismiss': 'modal', 'aria-label': 'Close' },
                                    React.createElement('span',{ 'aria-hidden': 'true' },'\xD7')),
                            React.createElement('h3',{ className: 'modal-title' },'Create new account')),
                            React.createElement('div',{ className: 'modal-body' },
                                React.createElement("label", {type: "text", htmlFor: "login"}, 
                                React.createElement('span',{ className: "glyphicon glyphicon-user" }), " Login: "),
                                React.createElement("input", {className:'form-control input-sm', type: "text", placeholder: "Login", ref: "login", id: "login"}),
                                React.createElement('br'),
                                React.createElement("label", {type: "text", htmlFor: "password"},
                                React.createElement('span',{ className: "glyphicon glyphicon-lock" }), " Password: "),
                                React.createElement("input", {className:'form-control input-sm', type: "password", placeholder: "Password", ref: "password", id: "password"}),
                                React.createElement('br'),
                                React.createElement("label", {type: "text", htmlFor: "name"}, 
                                React.createElement('span',{ className: "glyphicon glyphicon-pencil" }), " Name: "),
                                React.createElement("input", {className:'form-control input-sm', type: "text", placeholder: "Name", ref: "name", id: "name"}),
                                React.createElement('br'),
                                React.createElement("label", {type: "text", htmlFor: "email"},
                                React.createElement('span',{ className: "glyphicon glyphicon-envelope" }), " E-mail:"),
                                React.createElement("input", {className:'form-control input-sm', type: "text", placeholder: "Email", ref: "email", id: "email"})),
                                React.createElement('div',{ className: 'modal-footer' },
                                React.createElement('button',{ type: 'button', className: 'btn btn-primary', onClick: this.handleSubmit},
                                    'Register')
                            )
                        )
                    )
                )
        );
    },
    
    propTypes: {
        handleHideRegModal: React.PropTypes.func.isRequired
      }
    
});

//var renderSignup = function () {
//    React.render(
//        React.createElement(SignupForm),
//        document.getElementById("signUp")
//    );
//};
