var converter = new Showdown.converter();

var SigninForm = React.createClass({displayName: "LoginForm",
    
    componentDidMount: function componentDidMount(){
        $(this.getDOMNode()).modal('show');
        $(this.getDOMNode()).on('hidden.bs.modal', this.props.handleHideModal);
    },
    
    validateInput: function(loginCredentials){
       if (loginCredentials.username == "" && loginCredentials.password == "")
        {
            alert("Login and password required");
            return false;
        }
        if (loginCredentials.username == "" && loginCredentials.password != "")
        {
            alert("Login required");
            return false;
        }
        if (loginCredentials.username != "" && loginCredentials.password == "")
        {
            alert("Password required");
            return false;
        }
        
        return true;
    },
    
    handleSubmit: function (e) {
        e.preventDefault();
        var login = this.refs.login.getDOMNode().value.trim();
        var password = this.refs.password.getDOMNode().value.trim();
        var loginCredentials = {};
        loginCredentials.username = login;
        loginCredentials.password = password;
            
        if(this.validateInput(loginCredentials)){
            console.log(JSON.stringify(loginCredentials))
            $.ajax({
                url: "/loginReactAction",
                dataType: 'json',
                type: 'POST',
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(loginCredentials),
                success: function (data) {
    //                console.log(data)
                    if(data.errors == null) {
                        location = '/home';
                    }else
                    {
                        alert("Incorrect login/password");
                    }
                },
                error: function (xhr, status, err) {
                    console.error(status, err.toString());
                }
            });   
        }
        
        return 
    },
    
    render: function () {
        
        return (React.createElement('div',{ className: 'modal fade' },
                    React.createElement('div',{ className: 'modal-dialog' },
                        React.createElement('div',{ className: 'modal-content' },
                            React.createElement('div',{ className: 'modal-header' },
                                React.createElement('button',{ type: 'button', className: 'close',
                                    'data-dismiss': 'modal', 'aria-label': 'Close' },
                                    React.createElement('span',{ 'aria-hidden': 'true' },'\xD7')),
                            React.createElement('h3',{ className: 'modal-title' },'Sign in to your account')),
                            React.createElement('div',{ className: 'modal-body' },
                                React.createElement("label", {type: "text", htmlFor: "login"}, 
                                React.createElement('span',{ className: "glyphicon glyphicon-user" }), " Login: "),
                                React.createElement("input", {className:'form-control input-sm', type: "text", placeholder: "Login", ref: "login", id: "login"}),
                                React.createElement('br'),
                                React.createElement("label", {type: "text", htmlFor: "password"}, 
                                    React.createElement('span',{ className: "glyphicon glyphicon-lock" }), " Password: "),
                                React.createElement("input", {className:'form-control input-sm', type: "password", placeholder: "Password", ref: "password", id: "password", className: "form-control"}),
                                React.createElement('br'),
                                React.createElement('div', {className: 'container', align: "right"},
                                    React.createElement("label", {type: "text", htmlFor: "signUpAnch"}, "Don't have an account? "),
                                    React.createElement('br'),        
                                    React.createElement("a", { onClick: this.props.handleShowRegModal, href: '#'}, "Join us!"),
                                    React.createElement('br'), 
                                    React.createElement("a", { href: '#', onClick: this.props.handleShowResetModal}, "Forgot password?"))),
                            React.createElement('div',{ className: 'modal-footer' },
                                React.createElement('button',{ type: 'button', className: 'btn btn-primary', onClick: this.handleSubmit},
                                    'Sign in')
                                )
                            )
                        )
                    )
                );
    },
    
    propTypes: {
        handleHideModal: React.PropTypes.func.isRequired
      }
});