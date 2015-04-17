/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.examples.sprint;

import static org.junit.Assert.assertEquals;
import static org.tensorics.core.lang.Tensorics.builder;
import static org.tensorics.core.lang.Tensorics.from;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.lang.TensoricDoubleSupport;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.Context;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.TensorBuilder;

import com.google.common.collect.ImmutableSet;

public class SprintExample extends TensoricDoubleSupport {

	private static final Team TEAM_1 = new Team(1);
	private static final Team TEAM_3 = new Team(3);

	private Tensor<Double> velocity;
	private Tensor<Double> focusFactor;
	private Tensor<Double> team1Velocity;
	private Tensor<Double> team3Velocity;
	private Tensor<Double> mergedTensor;

	@Before
	public void setUp() {
		TensorBuilder<Double> velocityBuilder = builder(Team.class, NumberOfDay.class);
		TensorBuilder<Double> focusFactorBuilder = Tensorics.builder(Team.class);

		Random random = new Random();

		for (int teamId = 1; teamId <= 10; teamId++) {
			Team team = new Team(teamId);
			focusFactorBuilder.putAt(random.nextDouble(), team);
			for (int days = 1; days <= 20; days++) {
				velocityBuilder.putAt(10 * random.nextDouble(), team, new NumberOfDay(days));
			}
		}
		velocity = velocityBuilder.build();
		focusFactor = focusFactorBuilder.build();

		team1Velocity = from(velocity).extract(TEAM_1);
		team3Velocity = from(velocity).extract(TEAM_3);

		mergedTensor = Tensorics.merge(ImmutableSet.of(team1Velocity, team3Velocity));
	}

	@Test
	public void testApplyVelocity() {
		Tensor<Double> alteredVelocity = calculate(velocity).elementTimes(focusFactor);

		assertEquals(velocity.shape(), alteredVelocity.shape());
		for (Position position : velocity.shape().positionSet()) {
			Double velocityValue = velocity.get(position);
			Double focusFactorValue = focusFactor.get(position.coordinateFor(Team.class));
			Double alteredVelocityValue = alteredVelocity.get(position);
			assertEquals(velocityValue * focusFactorValue, alteredVelocityValue, 0);
		}
	}

	@Test
	public void testMergeEqualsPutAll() {
		TensorBuilder<Double> putAllTensorBuilder = builder(Team.class, NumberOfDay.class);
		putAllTensorBuilder.putAllAt(team1Velocity, TEAM_1);
		putAllTensorBuilder.putAllAt(team3Velocity, TEAM_3);
		Tensor<Double> putAllTensor = putAllTensorBuilder.build();

		assertEquals(putAllTensor, mergedTensor);
	}

	@Test
	public void testIfMergedTensorHasCorrectSize() {
		int differentNumberOfDays = velocity.shape().coordinatesOfType(NumberOfDay.class).size();
		int numberOfTeams = mergedTensor.shape().coordinatesOfType(Team.class).size();
		Tensor<Double> smallerTensor = calculate(velocity).times(mergedTensor);
		int expectedSize = differentNumberOfDays * numberOfTeams;
		assertEquals(expectedSize, mergedTensor.shape().size());
		assertEquals(expectedSize, smallerTensor.shape().size());
	}

	@Test
	public void testContext() {
		Context team1Context = team1Velocity.context();
		assertEquals(1, team1Context.getPosition().coordinates().size());

		Tensor<Double> sliceAtOneDay = from(team1Velocity).extract(new NumberOfDay(1));
		Context oneDayContext = sliceAtOneDay.context();
		assertEquals(2, oneDayContext.getPosition().coordinates().size());
	}

}
