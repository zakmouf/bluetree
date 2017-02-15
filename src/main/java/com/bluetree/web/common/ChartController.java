package com.bluetree.web.common;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimeSeriesCollection;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class ChartController extends AbstractController {

	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String name = request.getParameter("name");
		JFreeChart chart = null;
		if (!StringUtils.isBlank(name)) {
			chart = (JFreeChart) request.getSession().getAttribute(name);
		}
		if (chart == null) {
			chart = ChartFactory.createTimeSeriesChart(null, null, null, new TimeSeriesCollection(), false, false,
					false);
		}

		int width = Integer.parseInt(request.getParameter("width"));
		int height = Integer.parseInt(request.getParameter("height"));
		BufferedImage image = generateImage(chart, width, height);
		byte[] bytes = encodeImage(image);
		response.setContentType("image/jpeg");
		response.getOutputStream().write(bytes);

		return null;
	}

	protected BufferedImage generateImage(JFreeChart chart, int width, int height) {
		Dimension dimension = new Dimension(width, height);
		BufferedImage image = new BufferedImage((int) dimension.getWidth(), (int) dimension.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = image.createGraphics();
		Rectangle2D rectangle = new Rectangle(dimension);
		chart.draw(graphics, rectangle);
		graphics.dispose();
		return image;
	}

	protected byte[] encodeImage(BufferedImage image) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "jpeg", baos);
		byte[] bytes = baos.toByteArray();
		return bytes;
	}

}
