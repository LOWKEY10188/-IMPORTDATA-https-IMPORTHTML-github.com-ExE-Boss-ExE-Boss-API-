/*
 * Copyright (c) 2017 NOVA, All rights reserved.
 * This library is free software, licensed under GNU Lesser General Public License version 3
 *
 * This file is part of NOVA.
 *
 * NOVA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * NOVA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with NOVA.  If not, see <http://www.gnu.org/licenses/>.
 */

package nova.core.recipes.crafting;

import nova.core.item.Item;
import nova.core.item.ItemFactory;
import nova.core.recipes.ingredient.ItemIngredient;
import nova.testutils.FakeCraftingGrid;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;

import static nova.testutils.NovaAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author ExE Boss
 */
public class ShapedCraftingRecipeTest {

    public ShapedCraftingRecipeTest() {
    }

	private ItemFactory item1;
	private ItemFactory item2;
	private ItemFactory item3;

    @Before
    public void setUp() {
		item1 = new ItemFactory("test:item1", Item::new);
		item2 = new ItemFactory("test:item2", Item::new);
		item3 = new ItemFactory("test:item3", Item::new);
    }

	@Test
	@SuppressWarnings("unchecked")
	public void testMatches() {
		CraftingRecipe recipe = new ShapedCraftingRecipe(item1, "A- A", false, ItemIngredient.forItem(item2));

		// 2x2
		//  non-mirrored
		assertThat(recipe.matches(new FakeCraftingGrid(2, 2, Arrays.copyOf(new Optional<?>[]{
			Optional.of(item2.build()), Optional.empty(),
			Optional.empty(), Optional.of(item2.build())
		}, 4, Optional[].class)))).isTrue();

		//  mirrored
		assertThat(recipe.matches(new FakeCraftingGrid(2, 2, Arrays.copyOf(new Optional<?>[]{
			Optional.empty(), Optional.of(item2.build()),
			Optional.of(item2.build()), Optional.empty()
		}, 4, Optional[].class)))).isFalse();

		// 3x3
		//  non-mirrored
		assertThat(recipe.matches(new FakeCraftingGrid(3, 3, Arrays.copyOf(new Optional<?>[]{
			Optional.of(item2.build()), Optional.empty(), Optional.empty(),
			Optional.empty(), Optional.of(item2.build()), Optional.empty(),
			Optional.empty(), Optional.empty(), Optional.empty()
		}, 9, Optional[].class)))).isTrue();

		assertThat(recipe.matches(new FakeCraftingGrid(3, 3, Arrays.copyOf(new Optional<?>[]{
			Optional.empty(), Optional.of(item2.build()), Optional.empty(),
			Optional.empty(), Optional.empty(), Optional.of(item2.build()),
			Optional.empty(), Optional.empty(), Optional.empty()
		}, 9, Optional[].class)))).isTrue();

		assertThat(recipe.matches(new FakeCraftingGrid(3, 3, Arrays.copyOf(new Optional<?>[]{
			Optional.empty(), Optional.empty(), Optional.empty(),
			Optional.of(item2.build()), Optional.empty(), Optional.empty(),
			Optional.empty(), Optional.of(item2.build()), Optional.empty()
		}, 9, Optional[].class)))).isTrue();

		assertThat(recipe.matches(new FakeCraftingGrid(3, 3, Arrays.copyOf(new Optional<?>[]{
			Optional.empty(), Optional.empty(), Optional.empty(),
			Optional.empty(), Optional.of(item2.build()), Optional.empty(),
			Optional.empty(), Optional.empty(), Optional.of(item2.build())
		}, 9, Optional[].class)))).isTrue();

		//  mirrored
		assertThat(recipe.matches(new FakeCraftingGrid(3, 3, Arrays.copyOf(new Optional<?>[]{
			Optional.empty(), Optional.of(item2.build()), Optional.empty(),
			Optional.of(item2.build()), Optional.empty(), Optional.empty(),
			Optional.empty(), Optional.empty(), Optional.empty()
		}, 9, Optional[].class)))).isFalse();

		assertThat(recipe.matches(new FakeCraftingGrid(3, 3, Arrays.copyOf(new Optional<?>[]{
			Optional.empty(), Optional.empty(), Optional.of(item2.build()),
			Optional.empty(), Optional.of(item2.build()), Optional.empty(),
			Optional.empty(), Optional.empty(), Optional.empty()
		}, 9, Optional[].class)))).isFalse();

		assertThat(recipe.matches(new FakeCraftingGrid(3, 3, Arrays.copyOf(new Optional<?>[]{
			Optional.empty(), Optional.empty(), Optional.empty(),
			Optional.empty(), Optional.of(item2.build()), Optional.empty(),
			Optional.of(item2.build()), Optional.empty(), Optional.empty()
		}, 9, Optional[].class)))).isFalse();

		assertThat(recipe.matches(new FakeCraftingGrid(3, 3, Arrays.copyOf(new Optional<?>[]{
			Optional.empty(), Optional.empty(), Optional.empty(),
			Optional.empty(), Optional.empty(), Optional.of(item2.build()),
			Optional.empty(), Optional.of(item2.build()), Optional.empty(),
		}, 9, Optional[].class)))).isFalse();
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testMatchesMirrored() {
		CraftingRecipe recipe = new ShapedCraftingRecipe(item1, "A- A", true, ItemIngredient.forItem(item2));

		// 2x2
		//  non-mirrored
		assertThat(recipe.matches(new FakeCraftingGrid(2, 2, Arrays.copyOf(new Optional<?>[]{
			Optional.of(item2.build()), Optional.empty(),
			Optional.empty(), Optional.of(item2.build())
		}, 4, Optional[].class)))).isTrue();

		//  mirrored
		assertThat(recipe.matches(new FakeCraftingGrid(2, 2, Arrays.copyOf(new Optional<?>[]{
			Optional.empty(), Optional.of(item2.build()),
			Optional.of(item2.build()), Optional.empty()
		}, 4, Optional[].class)))).isTrue();

		// 3x3
		//  non-mirrored
		assertThat(recipe.matches(new FakeCraftingGrid(3, 3, Arrays.copyOf(new Optional<?>[]{
			Optional.of(item2.build()), Optional.empty(), Optional.empty(),
			Optional.empty(), Optional.of(item2.build()), Optional.empty(),
			Optional.empty(), Optional.empty(), Optional.empty()
		}, 9, Optional[].class)))).isTrue();

		assertThat(recipe.matches(new FakeCraftingGrid(3, 3, Arrays.copyOf(new Optional<?>[]{
			Optional.empty(), Optional.of(item2.build()), Optional.empty(),
			Optional.empty(), Optional.empty(), Optional.of(item2.build()),
			Optional.empty(), Optional.empty(), Optional.empty()
		}, 9, Optional[].class)))).isTrue();

		assertThat(recipe.matches(new FakeCraftingGrid(3, 3, Arrays.copyOf(new Optional<?>[]{
			Optional.empty(), Optional.empty(), Optional.empty(),
			Optional.of(item2.build()), Optional.empty(), Optional.empty(),
			Optional.empty(), Optional.of(item2.build()), Optional.empty()
		}, 9, Optional[].class)))).isTrue();

		assertThat(recipe.matches(new FakeCraftingGrid(3, 3, Arrays.copyOf(new Optional<?>[]{
			Optional.empty(), Optional.empty(), Optional.empty(),
			Optional.empty(), Optional.of(item2.build()), Optional.empty(),
			Optional.empty(), Optional.empty(), Optional.of(item2.build())
		}, 9, Optional[].class)))).isTrue();

		//  mirrored
		assertThat(recipe.matches(new FakeCraftingGrid(3, 3, Arrays.copyOf(new Optional<?>[]{
			Optional.empty(), Optional.of(item2.build()), Optional.empty(),
			Optional.of(item2.build()), Optional.empty(), Optional.empty(),
			Optional.empty(), Optional.empty(), Optional.empty()
		}, 9, Optional[].class)))).isTrue();

		assertThat(recipe.matches(new FakeCraftingGrid(3, 3, Arrays.copyOf(new Optional<?>[]{
			Optional.empty(), Optional.empty(), Optional.of(item2.build()),
			Optional.empty(), Optional.of(item2.build()), Optional.empty(),
			Optional.empty(), Optional.empty(), Optional.empty()
		}, 9, Optional[].class)))).isTrue();

		assertThat(recipe.matches(new FakeCraftingGrid(3, 3, Arrays.copyOf(new Optional<?>[]{
			Optional.empty(), Optional.empty(), Optional.empty(),
			Optional.empty(), Optional.of(item2.build()), Optional.empty(),
			Optional.of(item2.build()), Optional.empty(), Optional.empty()
		}, 9, Optional[].class)))).isTrue();

		assertThat(recipe.matches(new FakeCraftingGrid(3, 3, Arrays.copyOf(new Optional<?>[]{
			Optional.empty(), Optional.empty(), Optional.empty(),
			Optional.empty(), Optional.empty(), Optional.of(item2.build()),
			Optional.empty(), Optional.of(item2.build()), Optional.empty(),
		}, 9, Optional[].class)))).isTrue();
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testMatchesMultiIngredientMirrored() {
		CraftingRecipe recipe = new ShapedCraftingRecipe(item1, "AB-BA", true, ItemIngredient.forItem(item2), ItemIngredient.forItem(item3));

		// 2x2
		//  non-mirrored
		assertThat(recipe.matches(new FakeCraftingGrid(2, 2, Arrays.copyOf(new Optional<?>[]{
			Optional.of(item2.build()), Optional.of(item3.build()),
			Optional.of(item3.build()), Optional.of(item2.build())
		}, 4, Optional[].class)))).isTrue();

		//  mirrored
		assertThat(recipe.matches(new FakeCraftingGrid(2, 2, Arrays.copyOf(new Optional<?>[]{
			Optional.of(item3.build()), Optional.of(item2.build()),
			Optional.of(item2.build()), Optional.of(item3.build())
		}, 4, Optional[].class)))).isTrue();

		// 3x3
		//  non-mirrored
		assertThat(recipe.matches(new FakeCraftingGrid(3, 3, Arrays.copyOf(new Optional<?>[]{
			Optional.of(item2.build()), Optional.of(item3.build()), Optional.empty(),
			Optional.of(item3.build()), Optional.of(item2.build()), Optional.empty(),
			Optional.empty(), Optional.empty(), Optional.empty()
		}, 9, Optional[].class)))).isTrue();

		assertThat(recipe.matches(new FakeCraftingGrid(3, 3, Arrays.copyOf(new Optional<?>[]{
			Optional.empty(), Optional.of(item2.build()), Optional.of(item3.build()),
			Optional.empty(), Optional.of(item3.build()), Optional.of(item2.build()),
			Optional.empty(), Optional.empty(), Optional.empty()
		}, 9, Optional[].class)))).isTrue();

		assertThat(recipe.matches(new FakeCraftingGrid(3, 3, Arrays.copyOf(new Optional<?>[]{
			Optional.empty(), Optional.empty(), Optional.empty(),
			Optional.of(item2.build()), Optional.of(item3.build()), Optional.empty(),
			Optional.of(item3.build()), Optional.of(item2.build()), Optional.empty()
		}, 9, Optional[].class)))).isTrue();

		assertThat(recipe.matches(new FakeCraftingGrid(3, 3, Arrays.copyOf(new Optional<?>[]{
			Optional.empty(), Optional.empty(), Optional.empty(),
			Optional.empty(), Optional.of(item2.build()), Optional.of(item3.build()),
			Optional.empty(), Optional.of(item3.build()), Optional.of(item2.build())
		}, 9, Optional[].class)))).isTrue();

		//  mirrored
		assertThat(recipe.matches(new FakeCraftingGrid(3, 3, Arrays.copyOf(new Optional<?>[]{
			Optional.of(item3.build()), Optional.of(item2.build()), Optional.empty(),
			Optional.of(item2.build()), Optional.of(item3.build()), Optional.empty(),
			Optional.empty(), Optional.empty(), Optional.empty()
		}, 9, Optional[].class)))).isTrue();

		assertThat(recipe.matches(new FakeCraftingGrid(3, 3, Arrays.copyOf(new Optional<?>[]{
			Optional.empty(), Optional.of(item3.build()), Optional.of(item2.build()),
			Optional.empty(), Optional.of(item2.build()), Optional.of(item3.build()),
			Optional.empty(), Optional.empty(), Optional.empty()
		}, 9, Optional[].class)))).isTrue();

		assertThat(recipe.matches(new FakeCraftingGrid(3, 3, Arrays.copyOf(new Optional<?>[]{
			Optional.empty(), Optional.empty(), Optional.empty(),
			Optional.of(item3.build()), Optional.of(item2.build()), Optional.empty(),
			Optional.of(item2.build()), Optional.of(item3.build()), Optional.empty()
		}, 9, Optional[].class)))).isTrue();

		assertThat(recipe.matches(new FakeCraftingGrid(3, 3, Arrays.copyOf(new Optional<?>[]{
			Optional.empty(), Optional.empty(), Optional.empty(),
			Optional.empty(), Optional.of(item3.build()), Optional.of(item2.build()),
			Optional.empty(), Optional.of(item2.build()), Optional.of(item3.build()),
		}, 9, Optional[].class)))).isTrue();
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testMatchesMultiIngredient() {
		CraftingRecipe recipe = new ShapedCraftingRecipe(item1, "AB-BA", false, ItemIngredient.forItem(item2), ItemIngredient.forItem(item3));

		// 2x2
		assertThat(recipe.matches(new FakeCraftingGrid(2, 2, Arrays.copyOf(new Optional<?>[]{
			Optional.of(item2.build()), Optional.of(item3.build()),
			Optional.of(item3.build()), Optional.of(item2.build())
		}, 4, Optional[].class)))).isTrue();

		assertThat(recipe.matches(new FakeCraftingGrid(2, 2, Arrays.copyOf(new Optional<?>[]{
			Optional.of(item3.build()), Optional.of(item2.build()),
			Optional.of(item2.build()), Optional.of(item3.build())
		}, 4, Optional[].class)))).isFalse();

		// 3x3
		//  non-mirrored
		assertThat(recipe.matches(new FakeCraftingGrid(3, 3, Arrays.copyOf(new Optional<?>[]{
			Optional.of(item2.build()), Optional.of(item3.build()), Optional.empty(),
			Optional.of(item3.build()), Optional.of(item2.build()), Optional.empty(),
			Optional.empty(), Optional.empty(), Optional.empty()
		}, 9, Optional[].class)))).isTrue();

		assertThat(recipe.matches(new FakeCraftingGrid(3, 3, Arrays.copyOf(new Optional<?>[]{
			Optional.empty(), Optional.of(item2.build()), Optional.of(item3.build()),
			Optional.empty(), Optional.of(item3.build()), Optional.of(item2.build()),
			Optional.empty(), Optional.empty(), Optional.empty()
		}, 9, Optional[].class)))).isTrue();

		assertThat(recipe.matches(new FakeCraftingGrid(3, 3, Arrays.copyOf(new Optional<?>[]{
			Optional.empty(), Optional.empty(), Optional.empty(),
			Optional.of(item2.build()), Optional.of(item3.build()), Optional.empty(),
			Optional.of(item3.build()), Optional.of(item2.build()), Optional.empty()
		}, 9, Optional[].class)))).isTrue();

		assertThat(recipe.matches(new FakeCraftingGrid(3, 3, Arrays.copyOf(new Optional<?>[]{
			Optional.empty(), Optional.empty(), Optional.empty(),
			Optional.empty(), Optional.of(item2.build()), Optional.of(item3.build()),
			Optional.empty(), Optional.of(item3.build()), Optional.of(item2.build())
		}, 9, Optional[].class)))).isTrue();

		//  mirrored
		assertThat(recipe.matches(new FakeCraftingGrid(3, 3, Arrays.copyOf(new Optional<?>[]{
			Optional.of(item3.build()), Optional.of(item2.build()), Optional.empty(),
			Optional.of(item2.build()), Optional.of(item3.build()), Optional.empty(),
			Optional.empty(), Optional.empty(), Optional.empty()
		}, 9, Optional[].class)))).isFalse();

		assertThat(recipe.matches(new FakeCraftingGrid(3, 3, Arrays.copyOf(new Optional<?>[]{
			Optional.empty(), Optional.of(item3.build()), Optional.of(item2.build()),
			Optional.empty(), Optional.of(item2.build()), Optional.of(item3.build()),
			Optional.empty(), Optional.empty(), Optional.empty()
		}, 9, Optional[].class)))).isFalse();

		assertThat(recipe.matches(new FakeCraftingGrid(3, 3, Arrays.copyOf(new Optional<?>[]{
			Optional.empty(), Optional.empty(), Optional.empty(),
			Optional.of(item3.build()), Optional.of(item2.build()), Optional.empty(),
			Optional.of(item2.build()), Optional.of(item3.build()), Optional.empty()
		}, 9, Optional[].class)))).isFalse();

		assertThat(recipe.matches(new FakeCraftingGrid(3, 3, Arrays.copyOf(new Optional<?>[]{
			Optional.empty(), Optional.empty(), Optional.empty(),
			Optional.empty(), Optional.of(item3.build()), Optional.of(item2.build()),
			Optional.empty(), Optional.of(item2.build()), Optional.of(item3.build()),
		}, 9, Optional[].class)))).isFalse();
	}
}
