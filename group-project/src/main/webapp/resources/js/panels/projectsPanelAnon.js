var converter = new Showdown.converter();

var ProjectsPanelAnon = React.createClass({displayName: "projectsPanelAnon",
    getInitialState: function getInitialState() {
        return {view: {showManualModal: false}};
    },
    handleShowManualModal: function handleShowManualModal() {
        this.setState({view: {showManualModal: true}});
    },
    handleHideManualModal: function handleHideManualModal() {
        this.setState({view: {showManualModal: false}});
        $(".modal-backdrop.in").remove();
    },
    render: function () {
        return (React.createElement('div', null,
//                React.createElement('button', {id: 'manualBtn', type: 'button', className: 'btn btn-default btn-sm', onClick: this.handleShowManualModal},
//                                        React.createElement('span', {className: 'glyphicon glyphicon-book', 'aria-hidden': 'true'})),
//                                        React.createElement('br'),
//                                        React.createElement('br'),
                React.createElement('div', {className: "panel panel-primary"},
                React.createElement('div', {className: "panel-heading"}, "Projects  "),
                        React.createElement('div', {className: "panel-body"}, 
                        React.createElement('div', null, "Sign in to access your projects"))),
                                this.state.view.showManualModal ? React.createElement(ManualModal, {handleHideManualModal: this.handleHideManualModal}) : null
                
                ));
    },
  propTypes: {
        userStatus: React.PropTypes.bool
    }
});

var renderProjectsPanelAnon = function () {
    React.render(
            React.createElement(ProjectsPanelAnon),
            document.getElementById("projectsContainer")
            );
};

