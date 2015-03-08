package com.aemreunal.view.response.image.imageViewer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Set;
import javax.swing.*;
import com.aemreunal.view.response.image.ImageResponsePanel;

/**
 * <p> <code>NavigableImagePanel</code> is a lightweight container displaying an image
 * that can be zoomed in and out and panned with ease and simplicity, using an adaptive
 * rendering for high quality display and satisfactory performance. <h3>Image</h3> <p>An
 * image is loaded either via a constructor:</p>
 * <pre>
 * NavigableImagePanel panel = new NavigableImagePanel(image);
 * </pre>
 * or using a setter:
 * <pre>
 * NavigableImagePanel panel = new NavigableImagePanel();
 * panel.setImage(image);
 * </pre>
 * When an image is set, it is initially painted centered in the component, at the largest
 * possible size, fully visible, with its aspect ratio is preserved. This is defined as
 * 100% of the image size and its corresponding zoom level is 1.0. </p> <h3>Zooming</h3>
 * <p> Zooming can be controlled interactively, using either the mouse scroll wheel
 * (default) or the mouse two buttons, or programmatically, allowing the programmer to
 * implement other custom zooming methods. If the mouse does not have a scroll wheel, set
 * the zooming device to mouse buttons:
 * <pre>
 * panel.setZoomDevice(ZoomDevice.MOUSE_BUTTON);
 * </pre>
 * The left mouse button works as a toggle switch between zooming in and zooming out
 * modes, and the right button zooms an image by one increment (default is 20%). You can
 * change the zoom increment value by:
 * <pre>
 * panel.setZoomIncrement(newZoomIncrement);
 * </pre>
 * If you intend to provide programmatic zoom control, set the zoom device to none to
 * disable both the mouse wheel and buttons for zooming purposes:
 * <pre>
 * panel.setZoomDevice(ZoomDevice.NONE);
 * </pre>
 * and use <code>setZoom()</code> to change the zoom level. </p> <p> Zooming is always
 * around the point the mouse pointer is currently at, so that this point (called a
 * zooming center) remains stationary ensuring that the area of an image we are zooming
 * into does not disappear off the screen. The zooming center stays at the same location
 * on the screen and all other points move radially away from it (when zooming in), or
 * towards it (when zooming out). For programmatically controlled zooming the zooming
 * center is either specified when <code>setZoom()</code> is called:
 * <pre>
 * panel.setZoom(newZoomLevel, newZoomingCenter);
 * </pre>
 * or assumed to be the point of an image which is the closest to the center of the panel,
 * if no zooming center is specified:
 * <pre>
 * panel.setZoom(newZoomLevel);
 * </pre>
 * </p> <p> There are no lower or upper zoom level limits. </p> <h3>Navigation</h3>
 * <p><code>NavigableImagePanel</code> does not use scroll bars for navigation, but relies
 * on a navigation image located in the upper left corner of the panel. The navigation
 * image is a small replica of the image displayed in the panel. When you click on any
 * point of the navigation image that part of the image is displayed in the panel,
 * centered. The navigation image can also be zoomed in the same way as the main
 * image.</p> <p>In order to adjust the position of an image in the panel, it can be
 * dragged with the mouse, using the left button. </p> <p>For programmatic image
 * navigation, disable the navigation image:
 * <pre>
 * panel.setNavigationImageEnabled(false)
 * </pre>
 * and use <code>getImageOrigin()</code> and <code>setImageOrigin()</code> to move the
 * image around the panel. </p> <h3>Rendering</h3> <p><code>NavigableImagePanel</code>
 * uses the Nearest Neighbor interpolation for image rendering (default in Java). When the
 * scaled image becomes larger than the original image, the Bilinear interpolation is
 * applied, but only to the part of the image which is displayed in the panel. This
 * interpolation change threshold can be controlled by adjusting the value of
 * <code>HIGH_QUALITY_RENDERING_SCALE_THRESHOLD</code>.</p> <h3>DISCLAIMER by the author
 * of <a href="https://github.com/aemreunal/iBeaconServer">iBeacon Server</a></h3> This
 * code has been taken from <a href="https://today.java.net/pub/a/today/2007/03/27/navigable-image-panel.html">here</a>.
 * I would like to thank the author, Slav Boleslawski, for open sourcing his code, which I
 * have used it in the <a href="https://github.com/aemreunal/iBeaconServerManager">iBeacon
 * Server Manager</a>.
 */
public class ImageViewer extends JPanel {

    /**
     * <p>Defines whether the high quality rendering is enabled or not.</p>
     */
    private static final boolean HIGH_QUALITY_RENDERING_ENABLED = true;

    /**
     * <p>Identifies a change to the zoom level.</p>
     */
    public static final String ZOOM_LEVEL_CHANGED_PROPERTY = "zoomLevel";

    /**
     * <p>Identifies that the image in the panel has changed.</p>
     */
    public static final String IMAGE_CHANGED_PROPERTY = "image";

    /**
     * <p>Defines whether the navigation image is enabled or not.</p>
     */
    private static final boolean NAVIGATION_IMAGE_ENABLED = true;

    /**
     * <p>Defines whether the navigation image should be zoomable or not.</p>
     */
    public static final boolean ALLOW_NAV_IMG_ZOOM = false;

    /**
     * <p>Defines the sizes of beacon representations on the map.</p>
     */
    public static final int BEACON_OUTLINE_DIAMETER  = 16;
    public static final int BEACON_INTERIOR_DIAMETER = BEACON_OUTLINE_DIAMETER - 4;
    public static final int BEACON_CLICK_DIAMETER    = BEACON_OUTLINE_DIAMETER + 1;
    public static final int BEACON_CLICK_RADIUS      = BEACON_CLICK_DIAMETER / 2;

    private static final double SCREEN_NAV_IMG_FACTOR                  = 0.15; // 15% of panel's width
    private static final double NAV_IMG_FACTOR                         = 0.3; // 30% of panel's width
    private static final double HIGH_QUALITY_RENDERING_SCALE_THRESHOLD = 1.0;
    private static final int    NAV_IMG_OUTLINE_PADDING                = 1;
    private static final Object INTERPOLATION_TYPE                     = RenderingHints.VALUE_INTERPOLATION_BILINEAR;

    private final ImageResponsePanel imageResponsePanel;

    private BufferedImage image;
    private BufferedImage navigationImage;

    private double zoomIncrement = 0.2;
    private double zoomFactor    = 1.0 + zoomIncrement;
    private double navZoomFactor = 1.0 + zoomIncrement;

    private double initialScale = 0.0;
    private double scale        = 0.0;
    private double navScale     = 0.0;

    private int originX = 0;
    private int originY = 0;

    private int navImageWidth;
    private int navImageHeight;

    private Point     mousePosition;
    private Dimension previousPanelSize;

    // A set of beacons which represent the locations of beacons
    private Set<IVBeacon> beacons = null;

    /**
     * <p>Creates a new navigable image panel with the specified image and the mouse
     * scroll wheel as the zooming device.</p>
     */
    public ImageViewer(ImageResponsePanel imageResponsePanel, BufferedImage image) throws IOException {
        this.imageResponsePanel = imageResponsePanel;
        setOpaque(false);

        addComponentListener(new IVComponentAdapter(this));
        addMouseListener(new IVImgNavigationDevice(this)); // Used for jump-on-click at the navigation image
        addMouseMotionListener(new IVImgPanningDevice(this)); // Used for image panning via dragging with the mouse
        addMouseWheelListener(new IVWheelZoomDevice(this)); // Used for zooming to image with the mouse wheel
        addMouseListener(new IVImgClickDevice(this)); // Used for placing a beacon on clicked area

        setImage(image);
    }

    void clickedOnImageAt(IVCoords imageCoords) {
        for (IVBeacon beacon : beacons) {
            int radius = (int) Math.max((BEACON_CLICK_RADIUS * (1.0 / scale)), BEACON_CLICK_RADIUS);
            if (beacon.hasBeenClicked(imageCoords, radius)) {
                imageResponsePanel.clickedOnBeaconWithId(beacon.getBeaconId());
                return;
            }
        }
        imageResponsePanel.clickedOnImageAt(imageCoords.getIntX(), imageCoords.getIntY());
    }

    // Called from paintComponent() when a new image is set.
    private void initializeParams() {
        double xScale = (double) getWidth() / image.getWidth();
        double yScale = (double) getHeight() / image.getHeight();
        initialScale = Math.min(xScale, yScale);
        scale = initialScale;

        // An image is initially centered
        centerImage();
        if (isNavigationImageEnabled()) {
            createNavigationImage();
        }
    }

    // Centers the current image in the panel.
    void centerImage() {
        originX = (getWidth() - getScreenImageWidth()) / 2;
        originY = (getHeight() - getScreenImageHeight()) / 2;
    }

    // Creates and renders the navigation image in the upper let corner of the panel.
    void createNavigationImage() {
        // We keep the original navigation image larger than initially
        // displayed to allow for zooming into it without pixellation effect.
        navImageWidth = (int) (getWidth() * NAV_IMG_FACTOR);
        navImageHeight = navImageWidth * image.getHeight() / image.getWidth();
        int scrNavImageWidth = (int) (getWidth() * SCREEN_NAV_IMG_FACTOR);
        navScale = (double) scrNavImageWidth / navImageWidth;
        navigationImage = new BufferedImage(navImageWidth, navImageHeight, image.getType());
        Graphics g = navigationImage.getGraphics();
        g.drawImage(image, 0, 0, navImageWidth, navImageHeight, null);
    }

    /**
     * <p>Sets an image for display in the panel.</p>
     *
     * @param image
     *         an image to be set in the panel
     */
    void setImage(BufferedImage image) {
        BufferedImage oldImage = this.image;
        this.image = image;
        // Reset scale so that initializeParameters() is called in paintComponent()
        // for the new image.
        scale = 0.0;
        firePropertyChange(IMAGE_CHANGED_PROPERTY, oldImage, image);
        repaint();
    }

    // Converts this panel's coordinates into the original image coordinates
    IVCoords panelToImageCoords(Point p) {
        return new IVCoords((p.x - originX) / scale, (p.y - originY) / scale);
    }

    // Converts the original image coordinates into this panel's coordinates
    IVCoords imageToPanelCoords(IVCoords p) {
        return new IVCoords((p.x * scale) + originX, (p.y * scale) + originY);
    }

    // Converts the navigation image coordinates into the zoomed image coordinates
    private Point navToZoomedImageCoords(Point p) {
        int x = p.x * getScreenImageWidth() / getScreenNavImageWidth();
        int y = p.y * getScreenImageHeight() / getScreenNavImageHeight();
        return new Point(x, y);
    }

    // The user clicked within the navigation image and this part of the image
    // is displayed in the panel.
    // The clicked point of the image is centered in the panel.
    void displayImageAt(Point p) {
        Point scrImagePoint = navToZoomedImageCoords(p);
        originX = -(scrImagePoint.x - getWidth() / 2);
        originY = -(scrImagePoint.y - getHeight() / 2);
        repaint();
    }

    // Tests whether a given point in the panel falls within the image boundaries.
    boolean isInImage(Point p) {
        IVCoords coords = panelToImageCoords(p);
        int x = coords.getIntX();
        int y = coords.getIntY();
        return (x >= 0 && x < image.getWidth() && y >= 0 && y < image.getHeight());
    }

    // Tests whether a given point in the panel falls within the navigation image
    // boundaries.
    boolean isInNavigationImage(Point p) {
        return (isNavigationImageEnabled() && p.x < getScreenNavImageWidth()
                && p.y < getScreenNavImageHeight());
    }

    // Used when the image is resized.
    boolean isImageEdgeInPanel() {
        if (previousPanelSize == null) {
            return false;
        } else {
            return (originX > 0 && originX < previousPanelSize.width
                    || originY > 0 && originY < previousPanelSize.height);
        }

    }

    // Tests whether the image is displayed in its entirety in the panel.
    boolean isFullImageInPanel() {
        return (originX >= 0 && (originX + getScreenImageWidth()) < getWidth()
                && originY >= 0 && (originY + getScreenImageHeight()) < getHeight());
    }

    // High quality rendering kicks in when when a scaled image is larger
    // than the original image. In other words,
    // when image decimation stops and interpolation starts.
    private boolean isHighQualityRendering() {
        // Suppress 'pointless' inspection in IntelliJ
        //noinspection PointlessBooleanExpression,ConstantConditions
        return (HIGH_QUALITY_RENDERING_ENABLED && scale > HIGH_QUALITY_RENDERING_SCALE_THRESHOLD);
    }

    /**
     * <p>Indicates whether navigation image is enabled.<p>
     *
     * @return true when navigation image is enabled, false otherwise.
     */
    boolean isNavigationImageEnabled() {
        return NAVIGATION_IMAGE_ENABLED;
    }

    //Used when the panel is resized
    void scaleOrigin() {
        originX = originX * getWidth() / previousPanelSize.width;
        originY = originY * getHeight() / previousPanelSize.height;
        repaint();
    }

    /**
     * <p>Gets the current zoom level.</p>
     *
     * @return the current zoom level
     */
    double getZoom() {
        return scale / initialScale;
    }

    /**
     * <p>Gets the current zoom increment.</p>
     *
     * @return the current zoom increment
     */
    double getZoomIncrement() {
        return zoomIncrement;
    }

    //Zooms an image in the panel by repainting it at the new zoom level.
    //The current mouse position is the zooming center.
    void zoomImage() {
        IVCoords imageP = panelToImageCoords(mousePosition);
        double oldZoom = getZoom();
        scale *= zoomFactor;
        IVCoords panelP = imageToPanelCoords(imageP);

        originX += (mousePosition.x - (int) panelP.x);
        originY += (mousePosition.y - (int) panelP.y);

        firePropertyChange(ZOOM_LEVEL_CHANGED_PROPERTY, new Double(oldZoom), new Double(getZoom()));

        repaint();
    }

    //Zooms the navigation image
    void zoomNavigationImage() {
        navScale *= navZoomFactor;
        repaint();
    }

    //Moves te image (by dragging with the mouse) to a new mouse position p.
    void moveImage(Point p) {
        int xDelta = p.x - mousePosition.x;
        int yDelta = p.y - mousePosition.y;
        originX += xDelta;
        originY += yDelta;
        mousePosition = p;
        repaint();
    }

    //Gets the bounds of the image area currently displayed in the panel (in image
    //coordinates).
    private Rectangle getImageClipBounds() {
        IVCoords startCoords = panelToImageCoords(new Point(0, 0));
        IVCoords endCoords = panelToImageCoords(new Point(getWidth() - 1, getHeight() - 1));
        int panelX1 = startCoords.getIntX();
        int panelY1 = startCoords.getIntY();
        int panelX2 = endCoords.getIntX();
        int panelY2 = endCoords.getIntY();
        //No intersection?
        if (panelX1 >= image.getWidth() || panelX2 < 0 || panelY1 >= image.getHeight() || panelY2 < 0) {
            return null;
        }

        int x1 = (panelX1 < 0) ? 0 : panelX1;
        int y1 = (panelY1 < 0) ? 0 : panelY1;
        int x2 = (panelX2 >= image.getWidth()) ? image.getWidth() - 1 : panelX2;
        int y2 = (panelY2 >= image.getHeight()) ? image.getHeight() - 1 : panelY2;
        return new Rectangle(x1, y1, x2 - x1 + 1, y2 - y1 + 1);
    }

    /**
     * Paints the panel and its image at the current zoom level, location, and
     * interpolation method dependent on the image scale.</p>
     *
     * @param g
     *         the <code>Graphics</code> context for painting
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Paints the background

        if (image == null) {
            return;
        }

        if (scale == 0.0) {
            initializeParams();
        }

        if (isHighQualityRendering()) {
            Rectangle rect = getImageClipBounds();
            if (rect == null || rect.width == 0 || rect.height == 0) { // no part of image is displayed in the panel
                return;
            }

            BufferedImage subimage = image.getSubimage(rect.x, rect.y, rect.width,
                                                       rect.height);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, INTERPOLATION_TYPE);
            g2.drawImage(subimage, Math.max(0, originX), Math.max(0, originY),
                         Math.min((int) (subimage.getWidth() * scale), getWidth()),
                         Math.min((int) (subimage.getHeight() * scale), getHeight()), null);
        } else {
            g.drawImage(image, originX, originY, getScreenImageWidth(), getScreenImageHeight(), null);
        }

        if (beacons != null) {
            drawBeacons(g);
        }

        //Draw navigation image
        if (isNavigationImageEnabled()) {
            g.drawImage(navigationImage, 0, 0, getScreenNavImageWidth(), getScreenNavImageHeight(), null);
            drawNavigationImageOutline(g);
            drawZoomAreaOutline(g);
        }
    }

    private void drawBeacons(Graphics g) {
        Color previousColor = g.getColor();
        for (IVBeacon beacon : beacons) {
            IVCoords coords = imageToPanelCoords(beacon.getCoords());
            int x = coords.getIntX();
            int y = coords.getIntY();
            drawBeaconOutline(g, x, y);
            drawBeaconInterior(g, beacon.isDesignated(), x, y);
        }
        g.setColor(previousColor);
    }

    private void drawBeaconOutline(Graphics g, int x, int y) {
        g.setColor(Color.DARK_GRAY);
        int outlineDiameter = (int) Math.max((BEACON_OUTLINE_DIAMETER * scale), BEACON_OUTLINE_DIAMETER);
        g.fillOval(x - (outlineDiameter / 2), y - (outlineDiameter / 2), outlineDiameter, outlineDiameter);
    }

    private void drawBeaconInterior(Graphics g, boolean isDesignated, int x, int y) {
        if (isDesignated) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.BLUE);
        }
        int interiorDiameter = (int) Math.max((BEACON_INTERIOR_DIAMETER * scale), BEACON_INTERIOR_DIAMETER);
        g.fillOval(x - (interiorDiameter / 2), y - (interiorDiameter / 2), interiorDiameter, interiorDiameter);
    }

    private void drawNavigationImageOutline(Graphics g) {
        Color previousColor = g.getColor();
        g.setColor(Color.BLUE);
        g.drawRect(NAV_IMG_OUTLINE_PADDING,
                   NAV_IMG_OUTLINE_PADDING,
                   getScreenNavImageWidth() - (NAV_IMG_OUTLINE_PADDING * 2),
                   getScreenNavImageHeight() - (NAV_IMG_OUTLINE_PADDING * 2));
        g.setColor(previousColor);
    }

    //Paints a white outline over the navigation image indicating
    //the area of the image currently displayed in the panel.
    private void drawZoomAreaOutline(Graphics g) {
        if (isFullImageInPanel()) {
            return;
        }
        int x = -originX * getScreenNavImageWidth() / getScreenImageWidth();
        int y = -originY * getScreenNavImageHeight() / getScreenImageHeight();
        int width = getWidth() * getScreenNavImageWidth() / getScreenImageWidth();
        int height = getHeight() * getScreenNavImageHeight() / getScreenImageHeight();
        g.setColor(Color.RED);
        g.drawRect(x, y, width, height);
    }

    private int getScreenImageWidth() {
        return (int) (scale * image.getWidth());
    }

    private int getScreenImageHeight() {
        return (int) (scale * image.getHeight());
    }

    private int getScreenNavImageWidth() {
        return (int) (navScale * navImageWidth);
    }

    private int getScreenNavImageHeight() {
        return (int) (navScale * navImageHeight);
    }

    double getScale() {
        return scale;
    }

    void setZoomFactor(double zoomFactor) {
        this.zoomFactor = zoomFactor;
    }

    void setNavZoomFactor(double navZoomFactor) {
        this.navZoomFactor = navZoomFactor;
    }

    void setPreviousPanelSize(Dimension previousPanelSize) {
        this.previousPanelSize = previousPanelSize;
    }

    void setMousePosition(Point mousePosition) {
        this.mousePosition = mousePosition;
    }

    public void setBeacons(Set<IVBeacon> beacons) {
        this.beacons = beacons;
    }
}
