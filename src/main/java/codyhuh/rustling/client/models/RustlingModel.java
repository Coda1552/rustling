package codyhuh.rustling.client.models;

import codyhuh.rustling.common.entities.Rustling;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class RustlingModel<T extends Rustling> extends EntityModel<T> {

	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart eyes;

	public RustlingModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body = this.root.getChild("body");
		this.head = this.body.getChild("head");
		this.eyes = this.head.getChild("eyes");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -3.0F, -5.0F, 10.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -13.0F, -4.0F, 8.0F, 13.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(42, 3).addBox(-7.0F, -12.0F, 0.0F, 3.0F, 11.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(48, 3).addBox(4.0F, -12.0F, 0.0F, 3.0F, 11.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(42, 0).addBox(-4.0F, -16.0F, 0.0F, 8.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

		PartDefinition eyes = head.addOrReplaceChild("eyes", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -9.0F, -4.025F, 4.0F, 1.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Rustling pEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		Entity entity = Minecraft.getInstance().getCameraEntity();

		if (pEntity.getRustLevel()<3) {
			this.head.yScale = (float) (1.025 + Math.sin((ageInTicks*0.125F / (pEntity.getRustLevel() + 1))) * 0.025);

			this.head.yRot = -1 * 0.075F * Mth.cos(((0.5F * ageInTicks)/(pEntity.getRustLevel() + 1))/4);
		}

//		this.head.yRot += (float) (Math.cos((ageInTicks*360/2)/ (pEntity.getRustLevel() + 1))*5);

		if (entity != null) {
			Vec3 vec3 = entity.getEyePosition(0.0F);
			Vec3 vec31 = pEntity.getEyePosition(0.0F);
//			double d0 = vec3.y - vec31.y;
//			if (d0 > 0.0D) {
//				this.eyes.y = 0.0F;
//			} else {
//				this.eyes.y = 1.0F;
//			}

			Vec3 vec32 = pEntity.getViewVector(0.0F);
			vec32 = new Vec3(vec32.x, 0.0D, vec32.z);
			Vec3 vec33 = (new Vec3(vec31.x - vec3.x, 0.0D, vec31.z - vec3.z)).normalize().yRot(((float)Math.PI / 2F));
			double d1 = vec32.dot(vec33);
			this.eyes.x = (Mth.sqrt((float)Math.abs(d1)) * 2.0F * (float)Math.signum(d1))/2;
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}