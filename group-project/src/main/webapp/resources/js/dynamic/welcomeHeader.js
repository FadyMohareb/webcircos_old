var converter = new Showdown.converter();

var Welcome = React.createClass({displayName: "LoginForm",
    getInitialState: function getInitialState() {
        return { view: { showWelcome: false } };
      },
      
    handleShowModal: function handleShowModal() {
        this.setState({ view: { showWelcome: true } });
      },  
      
    render: function (username) {
        
        return (React.createElement('div', {className:"container"}, 
            React.createElement("p", {className: 'navbar-text'},  "Welcome " + username + "!"))
            );
    }
    
});


var renderWelcome = function () {
    React.render(
        React.createElement(Welcome),
        document.getElementById("upperLeftContainer")
    );
};