var converter = new Showdown.converter();

var FilesList = React.createClass({className: "filesList",
    render: function () {
        return (React.createElement('div', {className: "panel panel-primary"},
                    React.createElement('div', {className: "panel-heading"}, "Files"),
                    React.createElement('div', {className: "panel-body"}, 
                        React.createElement('div', {className: "panel panel-success"},
                            React.createElement('div', {className: "panel-heading"}, "Sequence"),
                            React.createElement('div', {className: "panel-body"}, "sequencefile1")),
                        React.createElement('div', {className: "panel panel-info"},
                            React.createElement('div', {className: "panel-heading"}, "Alignment"),
                            React.createElement('div', {className: "panel-body"}, "alignmentfile1")),
                        React.createElement('div', {className: "panel panel-warning"},
                            React.createElement('div', {className: "panel-heading"}, "Variants"),
                            React.createElement('div', {className: "panel-body"}, "variantfile1")),
                        React.createElement('div', {className: "panel panel-danger"},
                            React.createElement('div', {className: "panel-heading"}, "Expression"),
                            React.createElement('div', {className: "panel-body"}, "expressionfile1"))
            ))
          );
    }
})

var renderFiles = function () {
    React.render(React.createElement(FilesList), document.getElementById('leftContainer'));
};

