<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>WebCircos</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
        <script type="text/javascript" src="http://code.jquery.com/jquery-1.10.0.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
        <script src="${contextPath}/resources/js/vendor/react.js"></script>
        <script src="${contextPath}/resources/js/vendor/showdown.min.js"></script>
        <script src="${contextPath}/resources/js/home.js"></script>
        <script src="${contextPath}/resources/css/font-awesome.min.css"></script>
        <script src="${contextPath}/resources/js/modals/loginModal.js"></script>
        <script src="${contextPath}/resources/js/modals/registrationModal.js"></script>
        <script src="${contextPath}/resources/js/modals/resetPswdModal.js"></script>
        <script src="${contextPath}/resources/js/dynamic/accountDropdown.js"></script>
        <script src="${contextPath}/resources/js/panels/projectsPanel.js"></script>
        <script src="${contextPath}/resources/js/panels/filesPanel.js"></script>
        <script src="${contextPath}/resources/js/panels/filesGeneralPanel.js"></script>
        <script src="${contextPath}/resources/js/panels/filesTabContainer.js"></script>
        <script src="${contextPath}/resources/js/panels/filesParentPoolPanel.js"></script>
        <script src="${contextPath}/resources/js/panels/projectsPanelAnon.js"></script>
        <script src="${contextPath}/resources/js/tools/d3.js"></script>
        <script src="${contextPath}/resources/js/tools/biocircos-1.1.0.js"></script> 
        <script src="${contextPath}/resources/js/panels/circosPanel.js"></script>
        <script src="${contextPath}/resources/js/panels/parentPoolPanel.js"></script>
        <script src="${contextPath}/resources/js/panels/circosDisplayRoutines.js"></script>     
        <script src="${contextPath}/resources/js/dynamic/welcomeHeader.js"></script>
        <script src="${contextPath}/resources/js/modals/changePswdModal.js"></script>
        <script src="${contextPath}/resources/js/modals/uploadModal.js"></script>
        <script src="${contextPath}/resources/js/modals/importModalTest.js"></script>
        <script src="${contextPath}/resources/js/panels/centerTabs.js"></script>
        <script src="${contextPath}/resources/js/modals/newProjectModal.js"></script>
        <script src="${contextPath}/resources/js/datastructures/FileListStructure.js"></script>
        <script src="${contextPath}/resources/js/datastructures/BSAfileListStructure.js"></script>
        <script src="${contextPath}/resources/js/datastructures/ImportFileStructure.js"></script>
        <script src="https://rawgit.com/enyo/dropzone/master/dist/dropzone.js"></script>
        <link rel="stylesheet" href="https://rawgit.com/enyo/dropzone/master/dist/dropzone.css">
        <script src="${contextPath}/resources/css/bootstrap.min.css"></script>
    </head>
    <body>
        <script type="text/javascript">
            var Structure = {};
            $(function () {
                Structure = new FileListStructure();
            });
        </script>
        <script type="text/javascript">
            var BSAstructure = {};
            $(function () {
                BSAstructure = new BSAfileListStructure();
            });
        </script>
        <script type="text/javascript">
            var ImportStructure = {};
            $(function () {
                ImportStructure = new ImportFileStructure();
            });
        </script>
        <div class="row">  
            <security:authorize acess="isAuthenticated"></security>
                <c:if test="${pageContext.request.userPrincipal.name != null}">
                    <div id="upperLeftContainer" class="col-lg-3 page-header" style="float:left;margin:0px 20px 0px 30px">${upperLeftContainer} 
                        <h2>Welcome ${pageContext.request.userPrincipal.name}!</h2>
                    </div>
                    <div id="upperRightContainer" class="col-lg-3" style="float:right;margin:0px 40px 0px 40px">${upperRightContainer}</div>
                    <script type="text/javascript">
                        $(function () {
                            renderAccountDropdown('${pageContext.request.userPrincipal.name}');
                        });
                    </script>
                </c:if>
                <c:if test="${pageContext.request.userPrincipal.name == null}">
                    <div id="upperLeftContainer" class="col-lg-3" style="float:left;margin:0px 20px 20px 20px">${upperLeftContainer}
                        <script type="text/javascript">
                            $(function () {
                                renderHomePage('${pageContext.request.userPrincipal.name}');
                            });
                        </script>
                    </div>
                </c:if>
        </div>
        <div class="row" style="margin:0px 20px 0px 20px">
            <div class="row">
                <div class="col-lg-3" style="float:left;margin:0px 10px 0px 10px;width:25%">
                    <div id="projectsContainer" class="row">${projectsContainer}</div>
                    <c:if test="${pageContext.request.userPrincipal.name != null}">
                        <script type="text/javascript">
                            $(function () {
                                renderProjectsPanel();
                            });
                        </script>
                    </c:if>
                    <c:if test="${pageContext.request.userPrincipal.name == null}">
                        <script type="text/javascript">
                            $(function () {
                                renderProjectsPanelAnon();
                            });
                        </script>
                    </c:if>
                    <div id="filesContainer" class="row">${filesContainer}</div>
                    <script type="text/javascript">
                        $(function () {
                            renderFilesPanel('${pageContext.request.userPrincipal.name}');
                        });
                    </script>
                </div>
                <div id="centerContainer" class="col-lg-6" style="float:left;margin:0px 10px 0px 10px;width:72%">${centerContainer}</div>
                <script type="text/javascript">
                    $(function () {
                        renderCircosTabsComponent();
                    });
                </script>
            </div>
        </div>    
    </body>
</html>
