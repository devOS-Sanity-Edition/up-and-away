package one.devos.nautical.up_and_away.framework.ext;

import java.util.List;

import one.devos.nautical.up_and_away.content.balloon.entity.AbstractBalloon;

public interface EntityExt {
	void up_and_away$addBalloon(AbstractBalloon balloon);
	void up_and_away$removeBalloon(AbstractBalloon balloon);

	// do not modify this list
	List<AbstractBalloon> up_and_away$getBalloons();
}
