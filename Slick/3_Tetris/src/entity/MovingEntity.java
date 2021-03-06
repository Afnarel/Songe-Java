package entity;

public class MovingEntity extends Entity {
	
	protected float minScale;
	protected float maxScale;
	
	protected float scaleIncr;
	
	/* ************ *
	 * Constructors *
	 * ************ */
	
	public MovingEntity(String path) {
		super(path);
		initAttributes();
	}
	
	public MovingEntity(String path, float x, float y) {
		super(path,x,y);
		initAttributes();
	}
	
	public MovingEntity(String path, float x, float y, float scale) {
		super(path,x,y, scale);
		initAttributes();
	}
	
	public MovingEntity(String path, float x, float y, float scale, 
			float minScale, float maxScale, float scaleIncr) {
		super(path,x,y, scale);
		this.minScale = minScale;
		this.maxScale = maxScale;
		this.scaleIncr = scaleIncr;
	}
	
	@Override
	protected void initAttributes() {
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
	public void reduce(int delta) {
		scale -= (scale <= minScale) ? 0 : scaleIncr * delta;
		image.setCenterOfRotation(image.getWidth() / 2.0f * scale,
				image.getHeight() / 2.0f * scale);
	}
	
	/**
	 * Augment l'echelle de l'entite de scaleIncr, seulement si
	 * la taille de l'entite reste inferieure a minScale.
	 */
	public void enlarge(int delta) {
		scale += (scale >= maxScale) ? 0 : scaleIncr * delta;
		image.setCenterOfRotation(image.getWidth() / 2.0f * scale,
				image.getHeight() / 2.0f * scale);
	}
	
	/*
	public MovingEntity subEntity(int x, int y, int width, int height) {
		MovingEntity entity = (MovingEntity) super.subEntity(x, y, width, height);
		entity.initAttributes();
		return entity;
	}
	*/
	
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
	
	/* ********* *
	 * To String *
	 * ********* */
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("===== Moving entity =====").append("\n");
		sb.append(super.toString()).append("\n");
		sb.append("Min scale : ").append(minScale).append("\n");
		sb.append("Max scale : ").append(maxScale).append("\n");
		sb.append("Scale increment : ").append(scaleIncr);
		return sb.toString();
	}

}
