var converter = new Showdown.converter();

var UploadBox = React.createClass({className: "uploadBox",
    render: function () {
        return (React.createElement('div', {className: "panel panel-primary"},
                    React.createElement('div', {className: "panel-heading"}, "View settings"),
                    React.createElement('div', {className: "panel-body"},
                        React.createElement("h5", null, "View settings panel will be displayed here")
            ))
          );
    }
})

var renderUploadBox = function () {
    React.render(React.createElement(UploadBox), document.getElementById('rightContainer'));
};

