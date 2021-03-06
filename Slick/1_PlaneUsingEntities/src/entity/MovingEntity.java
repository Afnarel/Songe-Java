package entity;

public abstract class MovingEntity extends Entity {
	
	protected float minScale;
	protected float maxScale;
	
	protected float scaleIncr;
	
	/* ************ *
	 * Constructors *
	 * ************ */
	
	public MovingEntity(String path) {
		super(path);
		initArgs();
	}
	
	public MovingEntity(String path, float x, float y) {
		super(path,x,y);
		initArgs();
	}
	
	public MovingEntity(String path, float x, float y, float scale) {
		super(path,x,y, scale);
		initArgs();
	}
	
	public MovingEntity(String path, float x, float y, float scale, 
			float minScale, float maxScale, float scaleIncr) {
		super(path,x,y, scale);
		this.minScale = minScale;
		this.maxScale = maxScale;
		this.scaleIncr = scaleIncr;
	}
	
	private void initArgs() {
		this.minScale = 1.0f;
		this.maxScale = 5.0f;
		this.scaleIncr = 0.1f;
	}
	
	/* **************** *
	 * Public Functions *
	 * **************** */

	/**
	 * @param delta Coeff qui depend du nombre de FPS
	 * > 0 => Rotation droite
	 * < 0 => Rotation gauche
	 */
	public void rotate(int delta) {
		image.rotate(0.2f * delta);
	}
	
	/**
	 * @param delta Coeff qui depend du nombre de FPS
	 * > 0 => Avant
	 * < 0 => Arriere
	 */
	public void move(int delta) {
		float hip = 0.4f * delta;

		float rotation = image.getRotation();

		x += hip * Math.sin(Math.toRadians(rotation));
		y -= hip * Math.cos(Math.toRadians(rotation));
	}
	
	/**
	 * Reduit l'echelle de l'entite de scaleIncr, seulement si
	 * la taille de l'entite reste superieure a minScale.
	 */
	public void reduce() {
		scale -= (scale <= minScale) ? 0 : scaleIncr;
		image.setCenterOfRotation(image.getWidth() / 2.0f * scale,
				image.getHeight() / 2.0f * scale);
	}
	
	/**
	 * Augment l'echelle de l'entite de scaleIncr, seulement si
	 * la taille de l'entite reste inferieure a minScale.
	 */
	public void enlarge() {
		scale += (scale >= maxScale) ? 0 : scaleIncr;
		image.setCenterOfRotation(image.getWidth() / 2.0f * scale,
				image.getHeight() / 2.0f * scale);
	}
	
	/* ******* *
	 * Setters *
	 * ******* */
	
	public void setMinScale(float minScale) {
		this.minScale = minScale;
	}
	
	public void setMaxScale(float maxScale) {
		this.maxScale = maxScale;
	}
	
	public void setScaleIncr(float scaleIncr) {
		this.scaleIncr = scaleIncr;
	}

}
