/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BEAN;

import com.amon.db.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author amon.sabul
 */
@SessionScoped
@ManagedBean(name = "acc")

public class acc implements Serializable {

    @PersistenceContext(unitName = "roadsignerPU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private HtmlInputText latitude;
    private HtmlInputText longitude;
    private Usergroup group1 = new Usergroup();
    private List<Usergroup> group1List = new ArrayList<Usergroup>();
    private User user = new User();
    private List<User> userList = new ArrayList<User>();
    private Status status = new Status();
    private boolean remember;
    private UploadedFile file;
    private List<Status> statusList = new ArrayList<Status>();
    private Audit audit = new Audit();
    private List<Audit> auditList = new ArrayList<Audit>();
    private Signs signs = new Signs();
    private List<Signs> signList = new ArrayList<Signs>();
    private Roadsigns roadsigns = new Roadsigns();
    private List<Roadsigns> roadsignList = new ArrayList<Roadsigns>();
    private List<String> responsibilities;
    private List<String> deploymentUnitID;
    private String username = new String();
    private String password = new String();
    private Long signListCount;

    /**
     * Creates a new instance of acc
     */
    public acc() {
    }

    @PostConstruct
    public void init() {
        try {
            createRoadsignModel();
            setAdvancedModel(new DefaultMapModel());
            setRoadsignList((List<Roadsigns>) getEm().createQuery("select r from Roadsigns r").getResultList());
            for (Roadsigns c : getRoadsignList()) {
                getAdvancedModel().addOverlay(new Marker(new LatLng(Double.parseDouble(c.getLat()), Double.parseDouble(c.getLongitude())), "Road Name:" + c.getRoadName(), "images\barcode.gif", "http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private MapModel advancedModel;
    private Marker marker;

    private LineChartModel roadsignModel;

    private void createRoadsignModel() {
        setRoadsignList((List<Roadsigns>) getEm().createQuery("select r from Roadsigns r").getResultList());
        setRoadsignModel(new LineChartModel());
        LineChartSeries Cohort = new LineChartSeries();
        Cohort.setFill(true);
        Cohort.setLabel("Road Signs/Road locations");

        for (Roadsigns med : getRoadsignList()) {
            Cohort.set(med.getRoadName(), med.getIdsigns());
        }

        getRoadsignModel().addSeries(Cohort);
        getRoadsignModel().setTitle("Roadsign");
        getRoadsignModel().setLegendPosition("ne");
        getRoadsignModel().setStacked(true);
        getRoadsignModel().setShowPointLabels(true);

        Axis xAxis = new CategoryAxis("Places/Road occured");
        getRoadsignModel().getAxes().put(AxisType.X, xAxis);
        Axis yAxis = getRoadsignModel().getAxis(AxisType.Y);
        yAxis.setLabel("Roadsigns and places occured");
        yAxis.setMin(0);

    }

    public String login() {
        try {

            setUser((User) getEm().createQuery("select u from User u where u.username = '" + getUsername() + "' and u.pword = '" + getPassword() + "'").getSingleResult());
            setGroup1((Usergroup) getEm().createQuery("select u from Usergroup u where u.idgroups = " + getUser().getGroupID() + "").getSingleResult());
            getUtx().begin();
            getAudit().setAction("logged into the system at  " + new Date());
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getUtx().commit();

            return "index2.xhtml?faces-redirect=true";
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "Please provide correct credentials");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("loginInfoMessages", message);
            e.printStackTrace();
            return null;
        }
    }

    public Long getSignListCount() {
        this.signListCount = ((Long) this.getEm().createNativeQuery("select count(*) from roadsigns").getSingleResult());
        return this.signListCount;
    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getAttributes().clear();
        return "/index.xhtml?faces-redirect=true";
    }

    public MapModel getAdvancedModel() {
        return advancedModel;
    }

    public void onMarkerSelect(OverlaySelectEvent event) {
        setMarker((Marker) event.getOverlay());
    }

    public Marker getMarker() {
        return marker;
    }

    public boolean isRemember() {
        return this.remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    public String createUsergroup() {

        try {
            getUtx().begin();
            getAudit().setAction("created group");
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getGroup1().setCreatedBy(new User(1));
            getGroup1().setCreatedAt(new java.util.Date());
            getEm().persist(getAudit());
            getEm().persist(getGroup1());
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getUsergroup().getName() + " saved successfully."));
            setUsergroup(new Usergroup());
        } catch (Exception ex) {
            Logger.getLogger(acc.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not create a group."));
        }
        return null;
    }

    public String updateUsergroup() {
        try {
            Usergroup group2 = getEm().find(Usergroup.class,
                    getUsergroup().getIdgroups());
            getUtx().begin();
            getAudit().setAction("updated group");
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().merge(group2);
            getUtx().commit();
            group2 = new Usergroup();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", group2.getName() + " Updated successfully."));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update a group."));
        }
        setUsergroup(new Usergroup());
        return null;
    }

    public String deleteUsergroup(Usergroup group) {
        try {
            getUtx().begin();
            getAudit().setAction("Deleted group");
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            Usergroup toBeRemoved = (Usergroup) getEm().merge(group);
            getEm().remove(toBeRemoved);
            getUtx().commit();
            group = new Usergroup();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", group.getName() + " Deleted successfully."));
            return null;
        } catch (Exception e) {
            e.printStackTrace();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", group.getName() + " failed to delete successfully."));
        }
        group = new Usergroup();
        return null;
    }

    public String createUser() {
        try {

            getUser().setCreatedAt(new java.util.Date());
            getUser().setCreatedBy(1);
            getUser().setLastLogin(new java.util.Date());
            getUser().setPword("1234");
            getUser().setDepartment(1);
            getUtx().begin();
            getAudit().setAction("saved user " + getUser().getUsername());
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().persist(getUser());
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getUser().getName() + " saved successfully."));
            setUser(new User());
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
        }
        setUser(new User());
        return null;
    }

    public String updateUser() {
        try {

            User user = getEm().find(User.class,
                    getUser().getIdusers());
            getUtx().begin();
            getAudit().setAction("updated user " + user.getIdusers());
            getAudit().setCreatedby(user.getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().merge(user);
            getUtx().commit();
            user = new User();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", user.getName() + " Updated successfully."));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update a user."));
        }
        setUser(new User());
        return null;
    }

    public String deleteUser(User user) {
        try {

            getUtx().begin();
            getAudit().setAction("Deleted user");
            getAudit().setCreatedby(user.getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            User toBeRemoved = (User) getEm().merge(user);
            getEm().remove(toBeRemoved);
            getUtx().commit();
            user = new User();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "User deleted", "User deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("User", success);

            return null;
        } catch (Exception e) {
            e.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("User", success);
        }
        user = new User();
        return null;
    }

    public void createRoadsign(String latitude, String longitude) {
        try {

            System.out.println(latitude);
            System.out.println(longitude);

            roadsigns.setLat("1.233255");
            roadsigns.setLongitude("35.233255");
            roadsigns.setCreatedBy(1);
            roadsigns.setCreatedOn(new java.util.Date());
            roadsigns.setStatus(1);
            roadsigns.setSignid(roadsigns.getSignid());
            roadsigns.setDescription(roadsigns.getDescription());
            roadsigns.setDateinstalled(roadsigns.getDateinstalled());
            roadsigns.setPlaceName(roadsigns.getPlaceName());
            roadsigns.setRoadName(roadsigns.getRoadName());
            System.out.println(roadsigns.getLat() + " longitude " + longitude);

            getRoadsigns().setCreatedOn(new java.util.Date());
            getUtx().begin();
            getAudit().setAction("Registered road sign at " + getRoadsigns().getRoadName());
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().persist(getRoadsigns());
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "Roadsign along " + getRoadsigns().getRoadName() + " has been registered."));
            setRoadsigns(new Roadsigns());
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
            ex.printStackTrace();
        }
        setRoadsigns(new Roadsigns());

    }

    public String updateRoadsign() {
        try {

            Roadsigns sign = getEm().find(Roadsigns.class,
                    getRoadsigns().getIdsigns());
            getUtx().begin();
            getAudit().setAction("updated sign " + sign.getRoadName() + " " + sign.getDescription());
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().merge(sign);
            getUtx().commit();
            setRoadsigns(new Roadsigns());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", sign.getRoadName() + " Updated successfully."));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update an signent."));
        }
        setRoadsigns(new Roadsigns());
        return null;
    }

    public String deleteRoadsign(Roadsigns sign) {
        try {

            getUtx().begin();
            getAudit().setAction("Deleted road sign");
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            Roadsigns toBeRemoved = (Roadsigns) getEm().merge(sign);
            getEm().remove(toBeRemoved);
            getUtx().commit();
            setRoadsigns(new Roadsigns());
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Roadsign deleted", "Roadsign deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Roadsign", success);

            return null;
        } catch (Exception e) {
            e.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Roadsign", success);
        }
        setRoadsigns(new Roadsigns());
        return null;
    }

    public void createSign() {
        try {
            try {
                OutputStream out = new FileOutputStream(new File("C:\\Users\\Amon.sabul.CRAFTSILICON\\Documents\\NetBeansProjects\\roadSigner\\src\\main\\uploads" + file.getFileName()));
                int read = 0;
                byte[] bytes = new byte[1024];

                while ((read = file.getInputstream().read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                file.getInputstream().close();
                out.flush();
                out.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("reached here +++++++++++++++++++++++++++++++++");
            getSigns().setCreatedOn(new java.util.Date());
            getSigns().setPhoto(file.getFileName());
            getSigns().setCreatedBy(1);
            getSigns().setCreatedOn(new java.util.Date());
            getUtx().begin();
            getAudit().setAction("Registered road sign: " + getSigns().getSignname());
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().persist(getSigns());
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "Sign " + file.getFileName() + " is uploaded " + getSigns().getSignname() + " has been registered."));
            setSigns(new Signs());
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
            ex.printStackTrace();
        }
        setSigns(new Signs());
    }

    public String updateSign() {
        try {
            Signs sign = getEm().find(Signs.class,
                    getSigns().getIdsigns());
            getUtx().begin();
            getAudit().setAction("updated sign " + sign.getSignname() + " " + sign.getDescription());
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().merge(sign);
            getUtx().commit();
            setSigns(new Signs());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", sign.getSignname() + " Updated successfully."));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update an signent."));
        }
        setSigns(new Signs());
        return null;
    }

    public String deleteSign(Signs sign) {
        try {

            getUtx().begin();
            getAudit().setAction("Deleted road sign");
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            Signs toBeRemoved = (Signs) getEm().merge(sign);
            getEm().remove(toBeRemoved);
            getUtx().commit();
            setSigns(new Signs());
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sign deleted", "Sign deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Sign", success);

            return null;
        } catch (Exception e) {
            e.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Sign", success);
        }
        setSigns(new Signs());
        return null;
    }

    public String createStatus() {
        try {

            getStatus().setCreatedBy(getUser());
            getUtx().begin();
            getAudit().setAction("created status");
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().persist(status);
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getStatus().getName() + " saved successfully."));
            setStatus(new Status());
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
        }
        setStatus(new Status());
        return null;
    }

    public String updateStatus() {
        try {

            Status status = getEm().find(Status.class,
                    getStatus().getIdstatus());
            getUtx().begin();
            getAudit().setAction("updated segment");
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().merge(status);
            getUtx().commit();
            status = new Status();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", status.getName() + " Updated successfully."));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update a status."));
        }
        setStatus(new Status());
        return null;
    }

    public String deleteStatus(Status status) {
        try {

            getUtx().begin();
            getAudit().setAction("Deleted status");
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            Status toBeRemoved = (Status) getEm().merge(status);
            getEm().remove(toBeRemoved);
            getUtx().commit();
            status = new Status();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Status deleted", "Status deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Status", success);

            return null;
        } catch (Exception e) {
            e.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Status", success);
        }
        status = new Status();
        return null;
    }

    /**
     * @return the em
     */
    public EntityManager getEm() {
        return em;
    }

    /**
     * @param em the em to set
     */
    public void setEm(EntityManager em) {
        this.em = em;
    }

    /**
     * @return the utx
     */
    public javax.transaction.UserTransaction getUtx() {
        return utx;
    }

    /**
     * @param utx the utx to set
     */
    public void setUtx(javax.transaction.UserTransaction utx) {
        this.utx = utx;
    }

    /**
     * @return the group1
     */
    public Usergroup getUsergroup() {
        return getGroup1();
    }

    /**
     * @param group1 the group1 to set
     */
    public void setUsergroup(Usergroup group1) {
        this.setGroup1(group1);
    }

    /**
     * @return the group1List
     */
    public List<Usergroup> getUsergroupList() {
        setGroup1List((List<Usergroup>) getEm().createQuery("select g from Usergroup g").getResultList());
        return getGroup1List();
    }

    /**
     * @param group1List the group1List to set
     */
    public void setUsergroupList(List<Usergroup> group1List) {
        this.setGroup1List(group1List);
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the userList
     */
    public List<User> getUserList() {
        userList = getEm().createQuery("select u from User u").getResultList();
        return userList;
    }

    /**
     * @param userList the userList to set
     */
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    /**
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * @return the statusList
     */
    public List<Status> getStatusList() {
        statusList = getEm().createQuery("select s from Status s").getResultList();
        return statusList;
    }

    /**
     * @param statusList the statusList to set
     */
    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }

    /**
     * @return the audit
     */
    public Audit getAudit() {
        return audit;
    }

    /**
     * @param audit the audit to set
     */
    public void setAudit(Audit audit) {
        this.audit = audit;
    }

    /**
     * @return the auditList
     */
    public List<Audit> getAuditList() {
        auditList = getEm().createQuery("select a from Audit a").getResultList();
        return auditList;
    }

    /**
     * @param auditList the auditList to set
     */
    public void setAuditList(List<Audit> auditList) {
        this.auditList = auditList;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @param signent the signent to set
     */
    /**
     * @return the roadsignModel
     */
    public LineChartModel getRoadsignModel() {
        return roadsignModel;
    }

    public void setRoadsignModel(LineChartModel roadsignModel) {
        this.roadsignModel = roadsignModel;
    }

    public List<String> getDeploymentUnitID() {
        return deploymentUnitID;
    }

    public void setDeploymentUnitID(List<String> deploymentUnitID) {
        this.deploymentUnitID = deploymentUnitID;
    }

    public List<String> getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(List<String> responsibilities) {
        this.responsibilities = responsibilities;
    }

    public Usergroup getGroup1() {
        return group1;
    }

    public void setGroup1(Usergroup group1) {
        this.group1 = group1;
    }

    public List<Usergroup> getGroup1List() {
        return group1List;
    }

    public void setGroup1List(List<Usergroup> group1List) {
        this.group1List = group1List;
    }

    public HtmlInputText getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(HtmlInputText latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public HtmlInputText getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(HtmlInputText longitude) {
        this.longitude = longitude;
    }

    /**
     * @param advancedModel the advancedModel to set
     */
    public void setAdvancedModel(MapModel advancedModel) {
        this.advancedModel = advancedModel;
    }

    /**
     * @param marker the marker to set
     */
    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    /**
     * @return the signs
     */
    public Signs getSigns() {
        return signs;
    }

    /**
     * @param signs the signs to set
     */
    public void setSigns(Signs signs) {
        this.signs = signs;
    }

    /**
     * @return the signList
     */
    public List<Signs> getSignList() {
        signList = em.createQuery("select s from Signs s").getResultList();
        return signList;
    }

    /**
     * @param signList the signList to set
     */
    public void setSignList(List<Signs> signList) {
        this.signList = signList;
    }

    /**
     * @return the roadsigns
     */
    public Roadsigns getRoadsigns() {
        return roadsigns;
    }

    /**
     * @param roadsigns the roadsigns to set
     */
    public void setRoadsigns(Roadsigns roadsigns) {
        this.roadsigns = roadsigns;
    }

    /**
     * @return the roadsignList
     */
    public List<Roadsigns> getRoadsignList() {
        roadsignList = em.createQuery("select r from Roadsigns r").getResultList();
        return roadsignList;
    }

    /**
     * @param roadsignList the roadsignList to set
     */
    public void setRoadsignList(List<Roadsigns> roadsignList) {
        this.roadsignList = roadsignList;
    }

    /**
     * @param signListCount the signListCount to set
     */
    public void setAccidentListCount(Long signListCount) {
        this.signListCount = signListCount;
    }

    /**
     * @return the file
     */
    public UploadedFile getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(UploadedFile file) {
        this.file = file;
    }

}
