package com.requiem.abstractentities.entities;


import com.bulletphysics.collision.dispatch.CollisionObject;
import com.bulletphysics.collision.shapes.*;
import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.Transform;
import com.requiem.interfaces.Collidable;
import com.requiem.interfaces.Moveable;
import com.requiem.utilities.AssetManager;
import com.requiem.utilities.PhysicsUtils;
import com.requiem.utilities.renderutilities.Batch;
import com.trentwdavies.daeloader.Model;

import javax.vecmath.*;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Trent on 10/24/2014.
 */
public class Player implements Collidable, Moveable {
    public static final String MODEL_PATH = "assets/models/test-character-cube.dae";

    private Model model;
    private CollisionShape collisionShape;
    private RigidBody rigidBody;

    private Point3f pos;
    private Vector3f vel;
    private Vector3f ang;

    private static final float MASS = 2;
    private static final float MAX_SPEED = 6;
    private static final float ACCEL = 160;
    private static final float FRICTION = 80;
    private static final float RESTITUTION = 0.15f;
    private static final float MAX_STEEPNESS = (float) (Math.PI * 40 / 180);
    private static final float MAX_JUMP_HEIGHT = 2;//TODO calculate impulse force on space bar press using this

    private static final float WIDTH = 1f;
    private static final float HEIGHT = 2f;
    private static final float CCD_MOTION_THRESHOLD = Math.min(WIDTH, HEIGHT) / 2;//smallest radius to start doing continuous physics... I think it should be the smallest width of the person
    private static final float CCD_SPHERE_SWEPT_RADIUS = Math.min(WIDTH, HEIGHT) / 2;//should fit inside the person

    @Override
    public void init() {
        pos = new Point3f();
        vel = new Vector3f();
        ang = new Vector3f();
        model = (Model) AssetManager.getAsset(MODEL_PATH);

        float radius = Math.min(WIDTH, HEIGHT) / 2;
        float height = Math.max(0, HEIGHT - radius * 2);
        collisionShape = new CapsuleShape(radius, height);

        Vector3f localInertia = new Vector3f(0, 0, 0);
        collisionShape.calculateLocalInertia(MASS, localInertia);
        createRigidBody();

        setPos(new Point3f(5, 7, 1));
    }

    @Override
    public void update() {
        pos = new Point3f(rigidBody.getWorldTransform(new Transform()).origin);
        rigidBody.getLinearVelocity(vel);
    }

    @Override
    public void createRigidBody() {
        Vector3f localInertia = new Vector3f();
        collisionShape.calculateLocalInertia(MASS, localInertia);
        RigidBodyConstructionInfo constructionInfo = PhysicsUtils.createRigidBodyConstructionInfo(MASS, new Point3d(0, 0, 0), collisionShape, localInertia);
        constructionInfo.restitution = RESTITUTION;
        constructionInfo.friction = 0;
        rigidBody = new RigidBody(constructionInfo);
        rigidBody.setActivationState(CollisionObject.DISABLE_DEACTIVATION);
        rigidBody.setCcdMotionThreshold(CCD_MOTION_THRESHOLD);
        rigidBody.setCcdSweptSphereRadius(CCD_SPHERE_SWEPT_RADIUS);
    }

    @Override
    public void addToDynamicsWorld(DynamicsWorld dynamicsWorld) {
        dynamicsWorld.addRigidBody(rigidBody);
    }

    @Override
    public void render() {
        glPushMatrix();

        glTranslated(pos.x, pos.y - HEIGHT / 2, pos.z);
        glRotated(-ang.y, 0, 1, 0);

        Batch.renderModel(model);

        glPopMatrix();
    }

    @Override
    public String getModelPath() {
        return MODEL_PATH;
    }

    @Override
    public void setModelPath(String path) {
        //final
    }

    @Override
    public float getMaxSteepness() {
        return MAX_STEEPNESS;
    }

    @Override
    public void setMaxSteepness(float maxSteepness) {
        //final
    }

    @Override
    public float getMaxJumpHeight() {
        return MAX_JUMP_HEIGHT;
    }

    @Override
    public void setMaxJumpHeight(float maxJumpHeight) {
        //final
    }

    @Override
    public Point3f getPos() {
        return pos;
    }

    @Override
    public void setPos(Point3f pos) {
        Vector3f curPos = rigidBody.getWorldTransform(new Transform()).origin;
        pos.sub(curPos);
        rigidBody.translate(new Vector3f(pos));
    }

    @Override
    public Vector3f getVel() {
        return vel;
    }

    @Override
    public void setVel(Vector3f vel) {
        this.vel = vel;
    }

    @Override
    public Vector3f getAng() {
        return ang;
    }

    @Override
    public void setAng(Vector3f ang) {
        this.ang = ang;/*
        float pitchDiff = this.ang.x - ang.x;
        float yawDiff = this.ang.y - ang.y;
        float rollDiff = this.ang.z - ang.z;
System.out.println(ang);
System.out.println(pitchDiff + " - " + yawDiff + " - " + rollDiff);
        getAng().x += pitchDiff;
        getAng().y += yawDiff;
        getAng().z += rollDiff;*/
    }


    @Override
    public Model getModel() {
        return model;
    }

    @Override
    public void setModel(Model model) {
        this.model = model;
    }

    @Override
    public CollisionShape getCollisionShape() {
        return collisionShape;
    }

    @Override
    public void setCollisionShape(CollisionShape collisionShape) {
        this.collisionShape = collisionShape;
    }

    @Override
    public RigidBody getRigidBody() {
        return rigidBody;
    }

    @Override
    public void setRigidBody(RigidBody rigidBody) {
        this.rigidBody = rigidBody;
    }

    @Override
    public float getMass() {
        return MASS;
    }

    @Override
    public void setMass(float mass) {
        //final
    }

    @Override
    public float getMaxSpeed() {
        return MAX_SPEED;
    }

    @Override
    public void setMaxSpeed(float maxSpeed) {
        //final
    }

    @Override
    public float getAccel() {
        return ACCEL;
    }

    @Override
    public void setAccel(float accel) {
        //final
    }

    @Override
    public float getFriction() {
        return FRICTION;
    }

    @Override
    public void setFriction(float friction) {
        //final
    }
}
