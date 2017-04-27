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
        <script src="${contextPath}/resources/js/tools/jquery.cookie-1.4.1.min.js"></script>
        <script src="${contextPath}/resources/js/home.js"></script>
        <script src="${contextPath}/resources/css/font-awesome.min.css"></script>
        <script src="${contextPath}/resources/js/modals/loginModal.js"></script>
        <script src="${contextPath}/resources/js/modals/registrationModal.js"></script>
        <script src="${contextPath}/resources/js/dynamic/accountDropdown.js"></script>
        <script src="${contextPath}/resources/js/panels/projectsPanel.js"></script>
        <script src="${contextPath}/resources/js/panels/filesPanel.js"></script>
        <script src="${contextPath}/resources/js/panels/filesGeneralPanel.js"></script>
        <script src="${contextPath}/resources/js/panels/filesTabContainer.js"></script>
        <script src="${contextPath}/resources/js/panels/filesParentPoolPanel.js"></script>
        <script src="${contextPath}/resources/js/panels/projectsPanelAnon.js"></script>
        <script src="${contextPath}/resources/js/tools/d3.js"></script>
        <script src="${contextPath}/resources/js//tools/biocircos-1.1.0_v4_BSA.js"></script> 
        <script src="${contextPath}/resources/js/panels/circosPanel.js"></script>
        <script src="${contextPath}/resources/js/panels/parentPoolPanel.js"></script>
        <script src="${contextPath}/resources/js/panels/circosDisplayRoutines.js"></script>  
        <script src="${contextPath}/resources/js/modals/changePswdModal.js"></script>
        <script src="${contextPath}/resources/js/modals/removeModal.js"></script>
        <script src="${contextPath}/resources/js/modals/uploadModal.js"></script>
        <script src="${contextPath}/resources/js/modals/importModalTest.js"></script>
        <script src="${contextPath}/resources/js/panels/centerTabs.js"></script>
        <script src="${contextPath}/resources/js/modals/newProjectModal.js"></script>
        <script src="${contextPath}/resources/js/datastructures/FileListStructure.js"></script>
        <script src="${contextPath}/resources/js/datastructures/BSAfileListStructure.js"></script>
        <script src="https://rawgit.com/enyo/dropzone/master/dist/dropzone.js"></script>
        <link rel="stylesheet" href="https://rawgit.com/enyo/dropzone/master/dist/dropzone.css">
        <script src="${contextPath}/resources/css/bootstrap.min.css"></script>
        <script src="${contextPath}/resources/js/datastructures/ImportFileStructure.js"></script>
        <script src="${contextPath}/resources/js/datastructures/RemoveFileStructure.js"></script>
    </head>
    <body>
        <script type="text/javascript">
            var Structure = {};
            $(function () {
                Structure = new FileListStructure();
            });
            var BSAstructure = {};
            $(function () {
                BSAstructure = new BSAfileListStructure();
            });
            var ImportStructure = {};
            $(function () {
                ImportStructure = new ImportFileStructure();
            });
            var RemoveStructure = {};
            $(function () {
                RemoveStructure = new RemoveFileStructure();
            });
        </script>
        <div>
            <div class="col-lg-3" style="float:left" id='leftCol'>
                <div class="row" id="test">
                        <security:authorize acess="isAuthenticated"></security>
                        <div id="upperLeftContainer" class="col-lg-3" style="float:left">${upperLeftContainer}</div>
                            <c:if test="${pageContext.request.userPrincipal.name != null}">
                                 
                                    <!--<h2>Welcome <strong>${pageContext.request.userPrincipal.name}</strong>!</h2>-->
                                    <script type="text/javascript">
                                        $(function () {
                                            renderAccountDropdown('${pageContext.request.userPrincipal.name}');
                                        });
                                    </script>
                                
                                <!--<div id="upperRightContainer" class="col-lg-3" style="float:right;margin:0px 40px 0px 40px">${upperRightContainer}</div>-->

                            </c:if>
                            <c:if test="${pageContext.request.userPrincipal.name == null}">
                                <!--<div id="upperLeftContainer" class="col-lg-3" style="float:left;margin:0px 20px 20px 20px">${upperLeftContainer}-->
                                    <script type="text/javascript">
                                        $(function () {
                                            renderHomePage('${pageContext.request.userPrincipal.name}');
                                        });
                                    </script>
                                <!--</div>-->
                            </c:if>
                    </div>
                            <br><br>
                    <div id="projectsContainer">${projectsContainer}</div>
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
                    <div id="filesContainer">${filesContainer}</div>
                    <script type="text/javascript">
                        $(function () {
                            renderFilesPanel('${pageContext.request.userPrincipal.name}');
                        });
                    </script>
                </div>
                <div id="centerContainer" class="col-lg-9" style="float:right;width:73%">${centerContainer}</div>
                <script type="text/javascript">
                    $(function () {
                        renderCircosPanel();
                    });
                </script>
            </div>
        </div>    
    </body>
</html>
