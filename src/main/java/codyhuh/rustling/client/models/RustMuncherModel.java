package codyhuh.rustling.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class RustMuncherModel<T extends Entity> extends HierarchicalModel<T> {

	private final ModelPart root;
	private final ModelPart leg_l;
	private final ModelPart leg_r;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart arm_tr;
	private final ModelPart arm_tl;
	private final ModelPart arm_br;
	private final ModelPart arm_bl;

	public RustMuncherModel(ModelPart root) {
		this.root = root.getChild("root");
		this.head = this.root.getChild("head");
		this.body = this.root.getChild("body");
		this.arm_tr = this.body.getChild("arm_tr");
		this.arm_tl = this.body.getChild("arm_tl");
		this.arm_br = this.body.getChild("arm_br");
		this.arm_bl = this.body.getChild("arm_bl");
		this.leg_r = this.body.getChild("leg_r");
		this.leg_l = this.body.getChild("leg_l");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(20, 25).addBox(-1.0F, -4.25F, -13.25F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(0, 23).addBox(-4.0F, -5.25F, -3.25F, 8.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -16.75F, -0.75F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -17.0F, -4.0F, 12.0F, 15.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(28, 37).addBox(-1.0F, -15.0F, 0.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition arm_tr = body.addOrReplaceChild("arm_tr", CubeListBuilder.create(), PartPose.offset(6.0F, -14.5F, -0.5F));

		PartDefinition armrighttop_r1 = arm_tr.addOrReplaceChild("armrighttop_r1", CubeListBuilder.create().texOffs(40, 7).addBox(-2.0F, -4.0F, -1.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.5F, -0.5F, 0.0F, 0.0F, 1.5708F));

		PartDefinition arm_tl = body.addOrReplaceChild("arm_tl", CubeListBuilder.create(), PartPose.offset(-6.0F, -14.5F, -0.5F));

		PartDefinition armlefttop_r1 = arm_tl.addOrReplaceChild("armlefttop_r1", CubeListBuilder.create().texOffs(16, 37).addBox(-2.0F, -4.0F, -1.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 0.5F, -0.5F, 0.0F, 0.0F, 1.5708F));

		PartDefinition arm_br = body.addOrReplaceChild("arm_br", CubeListBuilder.create(), PartPose.offset(6.0F, -8.5F, 0.5F));

		PartDefinition armrightbottom_r1 = arm_br.addOrReplaceChild("armrightbottom_r1", CubeListBuilder.create().texOffs(28, 37).addBox(-2.0F, -4.0F, -1.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.5F, -0.5F, 0.0F, 0.0F, 1.5708F));

		PartDefinition arm_bl = body.addOrReplaceChild("arm_bl", CubeListBuilder.create(), PartPose.offset(-6.0F, -8.5F, -0.5F));

		PartDefinition armleftbottom_r1 = arm_bl.addOrReplaceChild("armleftbottom_r1", CubeListBuilder.create().texOffs(32, 0).addBox(-2.0F, -4.0F, -1.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.5F, -0.5F, 0.0F, 0.0F, 1.5708F));

		PartDefinition leg_r = body.addOrReplaceChild("leg_r", CubeListBuilder.create().texOffs(34, 23).addBox(-1.5F, -1.0F, -2.5F, 3.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, -3.0F, -0.5F));

		PartDefinition leg_l = body.addOrReplaceChild("leg_l", CubeListBuilder.create().texOffs(0, 35).addBox(-1.5F, -1.0F, -2.5F, 3.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, -3.0F, -0.5F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float pLimbSwing, float pLimbSwingAmount, float ageInTicks, float pNetHeadYaw, float pHeadPitch) {
		this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
		this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);

		this.leg_r.xRot = Mth.cos(pLimbSwing * 0.6662F) * 1.4F * pLimbSwingAmount;
		this.leg_l.xRot = Mth.cos(pLimbSwing * 0.6662F + (float)Math.PI) * 1.4F * pLimbSwingAmount;

		this.arm_tl.yRot = Mth.cos(pLimbSwing * 0.6662F + (float)Math.PI) * 1.4F * pLimbSwingAmount;
		this.arm_tr.yRot = Mth.cos(pLimbSwing * 0.6662F + (float)Math.PI) * 1.4F * pLimbSwingAmount;

		this.arm_br.yRot = Mth.cos(pLimbSwing * 0.6662F) * 1.4F * pLimbSwingAmount;
		this.arm_bl.yRot = Mth.cos(pLimbSwing * 0.6662F) * 1.4F * pLimbSwingAmount;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		if (this.young) {
			float l;
			poseStack.pushPose();
			l = 1.5f / 2;
			poseStack.scale(l, l, l);

			poseStack.translate(0.0f, 2.35f, 0f);
			this.head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
			poseStack.popPose();
			poseStack.pushPose();
			l = 1.0f / 2;
			poseStack.scale(l, l, l);
			poseStack.translate(0.0f, 3f, 0.0f);
			this.body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
			poseStack.popPose();

		}
		else {

			root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		}

	}

	@Override
	public ModelPart root() {
		return root;
	}
}