package nova.core.block;

import nova.core.component.ComponentProvider;
import nova.core.component.transform.BlockTransform;
import nova.core.entity.Entity;
import nova.core.event.CancelableEvent;
import nova.core.event.EventBus;
import nova.core.game.Game;
import nova.core.item.Item;
import nova.core.item.ItemBlock;
import nova.core.item.ItemFactory;
import nova.core.util.Direction;
import nova.core.util.Identifiable;
import nova.core.util.transform.vector.Vector3d;
import nova.core.util.transform.vector.Vector3i;
import nova.core.world.World;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * @author Calclavia
 */
public abstract class Block extends ComponentProvider implements Identifiable {

	public final EventBus<Stateful.LoadEvent> loadEvent = new EventBus<>();
	public final EventBus<Stateful.UnloadEvent> unloadEvent = new EventBus<>();

	public final EventBus<NeighborChangeEvent> neighborChangeEvent = new EventBus<>();
	public final EventBus<BlockPlaceEvent> placeEvent = new EventBus<>();
	public final EventBus<BlockRemoveEvent> removeEvent = new EventBus<>();
	public final EventBus<RightClickEvent> rightClickEvent = new EventBus<>();
	public final EventBus<LeftClickEvent> leftClickEvent = new EventBus<>();

	public final EventBus<DropEvent> dropEvent = new EventBus<>();

	/**
	 * Called when the block is registered.
	 */
	public void onRegister() {
		//Register the itemblock
		Game.items().register((args) -> new ItemBlock(factory()));
	}

	public ItemFactory getItemFactory() {
		return Game.items().getItemFactoryFromBlock(factory());
	}

	/**
	 * Called to get the BlockFactory that refers to this Block class.
	 * @return The {@link nova.core.block.BlockFactory} that refers to this
	 * Block class.
	 */
	public final BlockFactory factory() {
		return Game.blocks().getFactory(getID()).get();
	}

	public final BlockTransform transform() {
		return get(BlockTransform.class);
	}

	public final World world() {
		return transform().world();
	}

	public final Vector3i position() {
		return transform().position();
	}

	/**
	 * Get the x co-ordinate of the block.
	 * @return The x co-ordinate of the block.
	 */
	public final int x() {
		return position().x;
	}

	/**
	 * Get the y co-ordinate of the block.
	 * @return The y co-ordinate of the block.
	 */
	public final int y() {
		return position().y;
	}

	/**
	 * Get the z co-ordinate of the block.
	 * @return The z co-ordinate of the block.
	 */
	public final int z() {
		return position().z;
	}

	/**
	 * Gets the breaking difficulty for the block. 1 is the standard, regular
	 * block hardness of the game. {@code Double.infinity} is unbreakable.
	 * @return The breaking difficulty.
	 */
	public double getHardness() {
		return 1;
	}

	/**
	 * Gets the explosion resistance for the block. 1 is the standard, regular
	 * resistance of the game. {@code Double.infinity} is unexplodeable.
	 * @return The resistance.
	 */
	public double getResistance() {
		return 1;
	}

	/**
	 * Checks if this block can be replaced.
	 * @return True if this block can be replaced.
	 */
	public boolean canReplace() {
		return false;
	}

	/**
	 * Called when an ItemBlock tries to place a block in this position whether to displace the place position or not.
	 * If the ItemBlock does not displace the position, it will replace this block.
	 *
	 * @return True if by right clicking on this block, the placement of the new block should be displaced.
	 */
	public boolean shouldDisplacePlacement() {
		return true;
	}

	/**
	 * Block Events
	 */
	@CancelableEvent.Cancelable
	public static class NeighborChangeEvent extends CancelableEvent {
		public final Optional<Vector3i> neighborPosition;

		/**
		 * Called when a block next to this one changes (removed, placed, etc...).
		 * @param neighborPosition The position of the block that changed.
		 */
		public NeighborChangeEvent(Optional<Vector3i> neighborPosition) {
			this.neighborPosition = neighborPosition;
		}
	}

	public static class BlockPlaceEvent {

		/**
		 * The entity that placed the block
		 */
		public final Entity placer;

		/**
		 * Side placed on
		 */
		public final Direction side;

		/**
		 * Where the player clicked on the block for placement.
		 */
		public final Vector3d hit;

		/**
		 * The item used to place this block.
		 */
		public final Item item;

		/**
		 * Called when the block is placed.
		 */
		public BlockPlaceEvent(Entity placer, Direction side, Vector3d hit, Item item) {
			this.placer = placer;
			this.side = side;
			this.hit = hit;
			this.item = item;
		}
	}

	public static class BlockRemoveEvent {
		/**
		 * The entity that is removing the block
		 */
		public final Optional<Entity> entity;

		/**
		 * {@code true} if the block can be removed
		 */
		public boolean result = true;

		/**
		 * Called when the block is about to be removed.
		 */
		public BlockRemoveEvent(Optional<Entity> entity) {
			this.entity = entity;
		}
	}

	public static class RightClickEvent {
		/**
		 * The entity that clicked this object. Most likely a
		 * player.
		 */
		public final Entity entity;

		/**
		 * The side it was clicked.
		 */
		public final Direction side;

		/**
		 * The position it was clicked.
		 */
		public final Vector3d position;

		/**
		 * {@code true} if the right click action does something.
		 */
		public boolean result = false;

		public RightClickEvent(Entity entity, Direction side, Vector3d position) {
			this.entity = entity;
			this.side = side;
			this.position = position;
		}
	}

	public static class LeftClickEvent {
		/**
		 * The entity that clicked this object. Most likely a
		 * player.
		 */
		public final Entity entity;

		/**
		 * The side it was clicked.
		 */
		public final Direction side;

		/**
		 * The position it was clicked.
		 */
		public final Vector3d position;

		/**
		 * {@code true} if the right click action does something.
		 */
		public boolean result = false;

		public LeftClickEvent(Entity entity, Direction side, Vector3d position) {
			this.entity = entity;
			this.side = side;
			this.position = position;
		}
	}

	public static class DropEvent {

		public Set<Item> drops;

		public DropEvent(Block block) {
			this.drops = Collections.singleton(Game.items().getItemFromBlock(block.factory()).makeItem());
		}
	}
}
