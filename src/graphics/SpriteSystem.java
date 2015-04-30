package graphics;

import core.AbstractSystem;
import movement.PositionComponent;
import movement.RotationComponent;

public class SpriteSystem extends AbstractSystem {

    private PositionComponent position;
    private RotationComponent rotation;
    private SpriteComponent sprite;

    public SpriteSystem(PositionComponent position, RotationComponent rotation, SpriteComponent sprite) {
        this.position = position;
        this.rotation = rotation;
        this.sprite = sprite;
    }

    public SpriteSystem(PositionComponent position, SpriteComponent sprite) {
        this(position, new RotationComponent(), sprite);
    }

    @Override
    public void update() {
        sprite.imageIndex += sprite.imageSpeed;
        if (sprite.visible) {
            Graphics3D.drawSprite(sprite.getTexture(), position.pos, sprite.hor, sprite.ver, 90, rotation.rot, sprite.color);
        }
    }

}
