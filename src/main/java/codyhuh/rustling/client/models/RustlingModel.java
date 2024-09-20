package codyhuh.rustling.client.models;

import codyhuh.rustling.common.entities.Rustling;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class RustlingModel<T extends Rustling> extends EntityModel<T> {
	private final ModelPart bone;

	public RustlingModel(ModelPart root) {
		this.bone = root.getChild("bone");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -6.0F, 0.0F, 10.0F, 6.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 16).addBox(-9.0F, -19.0F, 1.0F, 8.0F, 13.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(42, 3).addBox(-12.0F, -18.0F, 5.0F, 3.0F, 11.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(48, 3).addBox(-1.0F, -18.0F, 5.0F, 3.0F, 11.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(42, 0).addBox(-9.0F, -22.0F, 5.0F, 8.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 24.0F, -5.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Rustling entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}