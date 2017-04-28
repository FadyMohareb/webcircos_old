var ProjectsPanelAnonim = React.createClass({displayName: "projectsPanelAnonim",
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
                React.createElement('div', {className: "panel panel-primary"},
                React.createElement('div', {className: "panel-heading"}, React.createElement('strong', null, "Projects")),
                        React.createElement('div', {className: "panel-body"}, 
                        React.createElement('div', null, "Sign in to access your projects"))),
                                this.state.view.showManualModal ? React.createElement(ManualModal, {handleHideManualModal: this.handleHideManualModal}) : null
                
                ));
    },
  propTypes: {
        userStatus: React.PropTypes.bool
    }
});

var renderProjectsPanelAnonim = function () {
    React.render(
            React.createElement(ProjectsPanelAnonim),
            document.getElementById("projectsContainer")
            );
};

